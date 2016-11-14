package com.liveramp.commons;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

public class Accessors {

  public static <T> Optional<T> firstOrAbsent(Iterable<T> c) {
    Preconditions.checkNotNull(c, "Null iterable");

    Iterator<T> iter = c.iterator();

    if (!iter.hasNext()) {
      return Optional.absent();
    } else {
      return Optional.of(first(c));
    }
  }

  public static <T> T first(Iterable<T> c) {
    Preconditions.checkNotNull(c, "Null iterable");

    Iterator<T> iter = c.iterator();
    Preconditions.checkArgument(iter.hasNext(), "Empty collection");

    return iter.next();
  }

  public static <T> T second(Iterable<T> c) {
    Preconditions.checkNotNull(c, "Null iterable");

    Iterator<T> iter = c.iterator();
    Preconditions.checkArgument(iter.hasNext(), "Empty iterable");

    iter.next();
    Preconditions.checkArgument(iter.hasNext(), "Not enough elements");

    return iter.next();
  }

  public static <T> T only(Iterable<T> c) {
    Preconditions.checkNotNull(c, "Null iterable");

    Iterator<T> iterator = c.iterator();
    Preconditions.checkArgument(iterator.hasNext(), "Empty iterable");

    T val = iterator.next();

    if (iterator.hasNext()) {
      throw new RuntimeException(
          String.format(
              "Iterable has more than one element. First element: %s, Second element: %s, Has third element: %s",
              val,
              iterator.next(),
              iterator.hasNext()
          )
      );
    }

    return val;
  }

  public static <T> T only(T[] ts) {
    Preconditions.checkNotNull(ts, "Array is null");
    Preconditions.checkArgument(ts.length == 1, String.format("Array has %d elements instead of just 1", ts.length));

    return ts[0];
  }

  public static <T> Optional<T> onlyOrAbsent(Iterable<T> c) throws TooManyElementsException {
    Preconditions.checkNotNull(c, "Null iterable");

    Iterator<T> iterator = c.iterator();

    if (!iterator.hasNext()) {
      return Optional.absent();
    }

    T val = iterator.next();

    if (iterator.hasNext()) {
      throw new TooManyElementsException(
          String.format(
              "Iterable has more than one element. First element: %s, Second element: %s, Has third element: %s",
              val,
              iterator.next(),
              iterator.hasNext()
          )
      );
    }

    return Optional.of(val);
  }

  public static <T> T last(List<T> c) {
    Preconditions.checkNotNull(c, "Null collection");
    Preconditions.checkArgument(!c.isEmpty(), "Empty collection");
    return c.get(c.size() - 1);
  }

  public static class TooManyElementsException extends Exception {
    TooManyElementsException(final String message) {
      super(message);
    }
  }

}
