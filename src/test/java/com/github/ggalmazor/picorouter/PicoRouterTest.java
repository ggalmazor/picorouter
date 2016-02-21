package com.github.ggalmazor.picorouter;

import javaslang.collection.Map;
import org.junit.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class PicoRouterTest {

  @Test
  public void matches_exact_paths() throws Exception {
    PicoRouter pr = PicoRouter.of("/foo/bar/baz");

    assertThat(pr.matches("/foo/bar/baz")).isTrue();
    assertThat(pr.matches("/foo/baz/bar")).isFalse();
    assertThat(pr.matches("/foo/bar")).isFalse();
  }

  @Test
  public void matches_dynamic_paths() throws Exception {
    PicoRouter pr = PicoRouter.of("/foo/:bar/baz");

    assertThat(pr.matches("/foo/bar/baz")).isTrue();
    assertThat(pr.matches("/foo/123/baz")).isTrue();
  }

  @Test
  public void returns_captured_values_on_dynamic_path_parts() throws Exception {
    PicoRouter pr = PicoRouter.of("/foo/:bar/baz");

    Map<String, String> values = pr.parse("/foo/123/baz");
    assertThat(values.get("bar").get()).isEqualTo("123");
  }

  @Test
  public void equality_rules() throws Exception {
    PicoRouter cocoOne = PicoRouter.of("coco");
    PicoRouter cocoTwo = PicoRouter.of("coco");
    assertThat(cocoOne).isEqualTo(cocoTwo);
    assertThat(cocoOne.hashCode()).isEqualTo(cocoTwo.hashCode());
  }

  @Test
  public void nice_toString() throws Exception {
    assertThat(PicoRouter.of("coco/tero").toString()).isEqualTo("coco/tero");
  }
}
