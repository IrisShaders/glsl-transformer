package io.github.douira.glsl_transformer.core;

import java.util.Collection;
import java.util.function.Supplier;

import io.github.douira.glsl_transformer.transform.*;

public class ConfigurableTransformation<T extends JobParameters> extends Transformation<T> {
  private Collection<CachingSupplier<?>> cachingSuppliers;
  private T lastJobParameters;

  protected <V> Supplier<V> swapSupplier(Supplier<V> currentSupplier, Supplier<V> newSupplier) {
    removeSupplier(currentSupplier);
    return cachingSupplier(newSupplier);
  }

  protected <V> Supplier<V> swapSupplier(Supplier<V> currentSupplier, V newValue) {
    return swapSupplier(currentSupplier, new ValueSupplier<V>(newValue));
  }

  protected void removeSupplier(Supplier<?> currentSupplier) {
    if (currentSupplier == null) {
      throw new IllegalStateException("The current supplier is null!");
    }
    if (CachingSupplier.class.isInstance(currentSupplier)) {
      cachingSuppliers.remove((CachingSupplier<?>) currentSupplier);
    }
  }

  protected <V> Supplier<V> cachingSupplier(Supplier<V> newSupplier) {
    if (newSupplier == null) {
      throw new IllegalStateException("The new supplier is null!");
    }
    if (CachingSupplier.class.isInstance(newSupplier)) {
      cachingSuppliers.remove((CachingSupplier<V>) newSupplier);
    }
    return newSupplier;
  }

  protected <V> Supplier<V> cachingSupplier(CachePolicy cachePolicy, Supplier<V> newSupplier) {
    return cachingSupplier(new CachingSupplier<V>(cachePolicy, newSupplier));
  }

  protected <V> Supplier<V> value(V newValue) {
    return new ValueSupplier<V>(newValue);
  }

  private void invalidateCachingSuppliers(CachePolicy fulfilledPolicy) {
    for (CachingSupplier<?> supplier : cachingSuppliers) {
      supplier.invalidate(fulfilledPolicy);
    }
  }

  @Override
  protected void triggerJobInternal() {
    var newJobParameters = getJobParameters();
    if (lastJobParameters == null || !lastJobParameters.equals(newJobParameters)) {
      invalidateCachingSuppliers(CachePolicy.ON_FIXED_PARAMETER_CHANGE);
      lastJobParameters = newJobParameters;
    } else {
      invalidateCachingSuppliers(CachePolicy.ON_JOB);
    }
  }
}
