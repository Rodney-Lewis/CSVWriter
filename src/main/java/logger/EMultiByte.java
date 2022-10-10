package logger;

public enum EMultiByte {
  KILOBYTE(1000L), KIBIBYTE(1024L), MEGABYTE(1000000L), MEBIBYTE(1048576L), GIGABYTE(
      1000000000L), GIGIBYTE(1073741824L);

  public final Long bytes;

  EMultiByte(Long bytes) {
    this.bytes = bytes;
  }

}
