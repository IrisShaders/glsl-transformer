#!/usr/bin/env python3
"""Derives the reference data of the typing tests from glslang.

Run from the repository root with a clone of https://github.com/KhronosGroup/glslang
in hidden/reference/glslang. See docs/development.md.

Two files in the snapshot directory of the typing tests are written:

- TypeCorpusTest.glslang: for every file of the glslang test corpus whether
  glslang accepts it without errors. Only files that are identical to the ones
  in the clone are classified, all others are "unknown".
- TypeDifferentialTest.glslang: the types that glslang infers, extracted from
  its typed AST dumps for the files that glslang accepts, that do not use the
  preprocessor and that the type analysis has no diagnostics for according to
  TypeCorpusTest.outcomes. Update that file first if the analysis changed.
"""
import os, re, collections
D = 'src/test/java/io/github/douira/glsl_transformer/ast/typing/__snapshots__/'
C = 'src/test/resources/glslang-test/'
G = 'hidden/reference/glslang/Test/baseResults/'

# ---- which files glslang accepts
REFERENCE = 'hidden/reference/glslang/Test/'
status = []
for name in sorted(os.listdir(C)):
    content = open(C + name, errors='replace').read()
    result = 'unknown'
    if (os.path.exists(REFERENCE + name) and open(REFERENCE + name, errors='replace').read() == content
            and os.path.exists(G + name + '.out')):
        errors = re.search(r'^ERROR', open(G + name + '.out', errors='replace').read(), re.M)
        result = 'rejected' if errors else 'accepted'
    status.append('%s %s' % (name, result))
open(D + 'TypeCorpusTest.glslang', 'w').write('\n'.join(status) + '\n')
print(len(status), 'corpus files classified')

# ---- the types glslang infers
outcomes = dict(l.rstrip('\n').split(' ', 1) for l in open(D + 'TypeCorpusTest.outcomes'))
glslang = dict(l.strip().split(' ', 1) for l in open(D + 'TypeCorpusTest.glslang'))
pp = re.compile(r'(?m)^[ \t]*#[ \t]*(define|undef|if|ifdef|ifndef|else|elif|endif)\b')
SCALAR = r'(float|int|uint|bool|double|float16_t|int8_t|uint8_t|int16_t|uint16_t|int64_t|uint64_t)'
PREFIX = {'float': '', 'int': 'i', 'uint': 'u', 'bool': 'b', 'double': 'd', 'float16_t': 'f16',
          'int8_t': 'i8', 'uint8_t': 'u8', 'int16_t': 'i16', 'uint16_t': 'u16', 'int64_t': 'i64', 'uint64_t': 'u64'}

def translate(desc):
    if 'structure{' in desc or 'block{' in desc or 'subroutine' in desc:
        return None
    desc = re.sub(r'layout\([^)]*\)', '', desc).strip()
    sizes = ''
    while True:
        m = re.search(r'(unsized |runtime-sized )?(\d+-element )?array of ', desc)
        if not m:
            break
        sizes += '[]' if m.group(1) or not m.group(2) else '[%s]' % m.group(2).split('-')[0]
        desc = desc[m.end():]
    m = re.search(r'(\d)-component vector of ' + SCALAR + '$', desc)
    if m:
        return PREFIX[m.group(2)] + 'vec' + m.group(1) + sizes
    m = re.search(r'(\d)X(\d) matrix of ' + SCALAR + '$', desc)
    if m:
        c, r = m.group(1), m.group(2)
        return PREFIX[m.group(3)] + 'mat' + (c if c == r else c + 'x' + r) + sizes
    m = re.search(r'(?:^|\s)(' + SCALAR[1:-1] + r'|void|atomic_uint|[iu]?sampler\w+|[iu]?image\w+)$', desc)
    if m:
        return m.group(1) + sizes
    return None

lines = []
files = facts = 0
for name in sorted(outcomes):
    if outcomes[name] != 'ok' or glslang.get(name) != 'accepted':
        continue
    if pp.search(open(C + name, errors='replace').read()):
        continue
    # reference results of tests that link multiple files contain several dumps
    if open(G + name + '.out', errors='replace').read().count('Shader version:') > 2:
        continue
    variables = collections.defaultdict(set)
    functions = collections.defaultdict(set)
    assignments = collections.Counter()
    for line in open(G + name + '.out', errors='replace'):
        if line.startswith('Linked') or line.startswith('// Module'):
            break
        m = re.match(r"^\d+:\S+\s+'(\w+)' \((.*)\)\s*$", line)
        if m:
            variables[m.group(1)].add(translate(m.group(2)))
        m = re.match(r"^\d+:\S+\s+Function Definition: (\w+)\(.*\( (.*)\)\s*$", line)
        if m:
            functions[m.group(1)].add(translate(m.group(2)))
        m = re.match(r"^\d+:\S+\s+move second child to first child \((.*)\)\s*$", line)
        if m:
            assignments[translate(m.group(1))] += 1
    result = []
    for variable, types in sorted(variables.items()):
        if len(types) == 1 and None not in types:
            result.append('var %s %s' % (variable, next(iter(types))))
    for function, types in sorted(functions.items()):
        if len(types) == 1 and None not in types:
            result.append('fn %s %s' % (function, next(iter(types))))
    if None not in assignments:
        for type, count in sorted(assignments.items()):
            result.append('assign %s %d' % (type, count))
    if result:
        files += 1
        facts += len(result)
        lines.append('# ' + name)
        lines += result
open(D + 'TypeDifferentialTest.glslang', 'w').write('\n'.join(lines) + '\n')
print(files, 'files', facts, 'facts')
