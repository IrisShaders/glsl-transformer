package io.github.douira.glsl_transformer;

public class SnapshotUtil {
  public static String inputOutputSnapshot(String input, String output) {
    return escapeSnapshotContent(input + "\n" + "<>".repeat(25) + "\n" + output);
  }

  public static String escapeSnapshotContent(String str) {
    return str.replace("\r\n", "\n") //normalize newlines
        .replace("\n\n\n", "\n.\n.\n")
        .replaceAll("\\n\\n$", "\n.\n.")
        .replaceAll("^\\n\\n", ".\n.\n");
  }
}
