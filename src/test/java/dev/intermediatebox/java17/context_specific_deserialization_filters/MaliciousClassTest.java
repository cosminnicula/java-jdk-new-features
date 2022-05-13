package dev.intermediatebox.java17.context_specific_deserialization_filters;

import dev.intermediatebox.java17.context_specific_deserialization_filters.malicious.MaliciousClass;
import dev.intermediatebox.java17.context_specific_deserialization_filters.trusted.TrustedClass;
import org.testng.annotations.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public final class MaliciousClassTest {
  @Test(expectedExceptions = {IOException.class})
  public void shouldPreventDeserializationFromMaliciousPackage() throws IOException, ClassNotFoundException {
    byte[] bytes = convertObjectToStream(new MaliciousClass());
    InputStream is = new ByteArrayInputStream(bytes);
    ObjectInputStream ois = new ObjectInputStream(is);

    ois.setObjectInputFilter(ObjectInputFilter.Config.createFilter(
        "maxbytes=1024;dev.intermediatebox.java17.context_specific_deserialization_filters.trusted.*;java.base/*;!*")
    );

    Object obj = ois.readObject();
  }

  @Test
  public void shouldAllowDeserializationFromTrustedPackage() throws IOException, ClassNotFoundException {
    byte[] bytes = convertObjectToStream(new TrustedClass());
    InputStream is = new ByteArrayInputStream(bytes);
    ObjectInputStream ois = new ObjectInputStream(is);

    ois.setObjectInputFilter(ObjectInputFilter.Config.createFilter(
        "maxbytes=1024;dev.intermediatebox.java17.context_specific_deserialization_filters.trusted.*;java.base/*;!*")
    );

    Object obj = ois.readObject();

    assertThat(obj.toString()).isNotEmpty();
  }

  private static byte[] convertObjectToStream(Object obj) {
    ByteArrayOutputStream boas = new ByteArrayOutputStream();
    try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
      ois.writeObject(obj);
      return boas.toByteArray();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    throw new RuntimeException();
  }
}