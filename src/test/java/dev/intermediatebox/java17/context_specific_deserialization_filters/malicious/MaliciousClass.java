package dev.intermediatebox.java17.context_specific_deserialization_filters.malicious;

import java.io.Serializable;

public class MaliciousClass implements Serializable {
  @Override
  public String toString() {
    return "Cryptojacking in progress...";
  }
}