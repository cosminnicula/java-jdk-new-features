package dev.intermediatebox.java17.context_specific_deserialization_filters.trusted;

import java.io.Serializable;

public class TrustedClass implements Serializable {
  @Override
  public String toString() {
    return "Processing some safe business logic...";
  }
}