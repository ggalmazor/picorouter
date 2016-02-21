package com.github.ggalmazor.picorouter;

import javaslang.Tuple2;
import javaslang.collection.List;
import javaslang.collection.Map;

import java.util.Objects;

public class PicoRouter {
  private static final String PARTS_SEPARATOR = "/";
  private static final String DYNAMIC_PART_PREFIX = ":";
  private final List<String> parts;

  private PicoRouter(List<String> parts) {
    this.parts = parts;
  }

  public static PicoRouter of(String path) {
    return new PicoRouter(List.of(path.split(PARTS_SEPARATOR)));
  }

  public boolean matches(String path) {
    List<Tuple2<String, String>> zipped = parts.zip(PicoRouter.of(path).parts);
    return zipped.length() == parts.length()
        && zipped.forAll(t -> isDynamic(t) || isEqual(t));
  }

  public Map<String, String> parse(String path) {
    return parts.zip(PicoRouter.of(path).parts)
        .filter(this::isDynamic)
        .map(t -> t.<String, String>map(l -> l.substring(1), r -> r))
        .toMap(f -> f);
  }

  private boolean isDynamic(Tuple2<String, String> t) {
    return t._1.startsWith(DYNAMIC_PART_PREFIX);
  }

  private boolean isEqual(Tuple2<String, String> t) {
    return t._1.equals(t._2);
  }

  @Override
  public String toString() {
    return parts.mkString(PARTS_SEPARATOR);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PicoRouter that = (PicoRouter) o;
    return Objects.equals(parts, that.parts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(parts);
  }
}
