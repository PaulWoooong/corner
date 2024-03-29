// Generated by the protocol buffer compiler.  DO NOT EDIT!

package corner.model;

public final class PaginationProtoBuffer {
  private PaginationProtoBuffer() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public static final class Pagination extends
      com.google.protobuf.GeneratedMessage {
    // Use Pagination.newBuilder() to construct.
    private Pagination() {}
    
    private static final Pagination defaultInstance = new Pagination();
    public static Pagination getDefaultInstance() {
      return defaultInstance;
    }
    
    public Pagination getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return corner.model.PaginationProtoBuffer.internal_static_corner_model_Pagination_descriptor;
    }
    
    @Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return corner.model.PaginationProtoBuffer.internal_static_corner_model_Pagination_fieldAccessorTable;
    }
    
    // optional int64 totalRecord = 1 [default = 0];
    private boolean hasTotalRecord;
    private long totalRecord_ = 0L;
    public boolean hasTotalRecord() { return hasTotalRecord; }
    public long getTotalRecord() { return totalRecord_; }
    
    // optional int32 page = 2 [default = 1];
    private boolean hasPage;
    private int page_ = 1;
    public boolean hasPage() { return hasPage; }
    public int getPage() { return page_; }
    
    // optional int32 perPage = 3 [default = 10];
    private boolean hasPerPage;
    private int perPage_ = 10;
    public boolean hasPerPage() { return hasPerPage; }
    public int getPerPage() { return perPage_; }
    
    // repeated .corner.model.Parameter parameters = 4;
    private java.util.List<corner.model.PaginationProtoBuffer.Parameter> parameters_ =
      java.util.Collections.emptyList();
    public java.util.List<corner.model.PaginationProtoBuffer.Parameter> getParametersList() {
      return parameters_;
    }
    public int getParametersCount() { return parameters_.size(); }
    public corner.model.PaginationProtoBuffer.Parameter getParameters(int index) {
      return parameters_.get(index);
    }
    
    @Override
    public final boolean isInitialized() {
      for (corner.model.PaginationProtoBuffer.Parameter element : getParametersList()) {
        if (!element.isInitialized()) return false;
      }
      return true;
    }
    
    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (hasTotalRecord()) {
        output.writeInt64(1, getTotalRecord());
      }
      if (hasPage()) {
        output.writeInt32(2, getPage());
      }
      if (hasPerPage()) {
        output.writeInt32(3, getPerPage());
      }
      for (corner.model.PaginationProtoBuffer.Parameter element : getParametersList()) {
        output.writeMessage(4, element);
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    @Override
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (hasTotalRecord()) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(1, getTotalRecord());
      }
      if (hasPage()) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, getPage());
      }
      if (hasPerPage()) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(3, getPerPage());
      }
      for (corner.model.PaginationProtoBuffer.Parameter element : getParametersList()) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(4, element);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static corner.model.PaginationProtoBuffer.Pagination parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static corner.model.PaginationProtoBuffer.Pagination parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static corner.model.PaginationProtoBuffer.Pagination parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static corner.model.PaginationProtoBuffer.Pagination parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static corner.model.PaginationProtoBuffer.Pagination parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static corner.model.PaginationProtoBuffer.Pagination parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static corner.model.PaginationProtoBuffer.Pagination parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static corner.model.PaginationProtoBuffer.Pagination parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return new Builder(); }
    public Builder newBuilderForType() { return new Builder(); }
    public static Builder newBuilder(corner.model.PaginationProtoBuffer.Pagination prototype) {
      return new Builder().mergeFrom(prototype);
    }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      // Construct using corner.model.PaginationProtoBuffer.Pagination.newBuilder()
      private Builder() {}
      
      corner.model.PaginationProtoBuffer.Pagination result = new corner.model.PaginationProtoBuffer.Pagination();
      
      @Override
      protected corner.model.PaginationProtoBuffer.Pagination internalGetResult() {
        return result;
      }
      
      @Override
      public Builder clear() {
        result = new corner.model.PaginationProtoBuffer.Pagination();
        return this;
      }
      
      @Override
      public Builder clone() {
        return new Builder().mergeFrom(result);
      }
      
      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return corner.model.PaginationProtoBuffer.Pagination.getDescriptor();
      }
      
      public corner.model.PaginationProtoBuffer.Pagination getDefaultInstanceForType() {
        return corner.model.PaginationProtoBuffer.Pagination.getDefaultInstance();
      }
      
      public corner.model.PaginationProtoBuffer.Pagination build() {
        if (!isInitialized()) {
          throw new com.google.protobuf.UninitializedMessageException(
            result);
        }
        return buildPartial();
      }
      
      private corner.model.PaginationProtoBuffer.Pagination buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw new com.google.protobuf.UninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public corner.model.PaginationProtoBuffer.Pagination buildPartial() {
        if (result.parameters_ != java.util.Collections.EMPTY_LIST) {
          result.parameters_ =
            java.util.Collections.unmodifiableList(result.parameters_);
        }
        corner.model.PaginationProtoBuffer.Pagination returnMe = result;
        result = null;
        return returnMe;
      }
      
      @Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof corner.model.PaginationProtoBuffer.Pagination) {
          return mergeFrom((corner.model.PaginationProtoBuffer.Pagination)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(corner.model.PaginationProtoBuffer.Pagination other) {
        if (other == corner.model.PaginationProtoBuffer.Pagination.getDefaultInstance()) return this;
        if (other.hasTotalRecord()) {
          setTotalRecord(other.getTotalRecord());
        }
        if (other.hasPage()) {
          setPage(other.getPage());
        }
        if (other.hasPerPage()) {
          setPerPage(other.getPerPage());
        }
        if (!other.parameters_.isEmpty()) {
          if (result.parameters_.isEmpty()) {
            result.parameters_ = new java.util.ArrayList<corner.model.PaginationProtoBuffer.Parameter>();
          }
          result.parameters_.addAll(other.parameters_);
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input)
          throws java.io.IOException {
        return mergeFrom(input,
          com.google.protobuf.ExtensionRegistry.getEmptyRegistry());
      }
      
      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistry extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 8: {
              setTotalRecord(input.readInt64());
              break;
            }
            case 16: {
              setPage(input.readInt32());
              break;
            }
            case 24: {
              setPerPage(input.readInt32());
              break;
            }
            case 34: {
              corner.model.PaginationProtoBuffer.Parameter.Builder subBuilder = corner.model.PaginationProtoBuffer.Parameter.newBuilder();
              input.readMessage(subBuilder, extensionRegistry);
              addParameters(subBuilder.buildPartial());
              break;
            }
          }
        }
      }
      
      
      // optional int64 totalRecord = 1 [default = 0];
      public boolean hasTotalRecord() {
        return result.hasTotalRecord();
      }
      public long getTotalRecord() {
        return result.getTotalRecord();
      }
      public Builder setTotalRecord(long value) {
        result.hasTotalRecord = true;
        result.totalRecord_ = value;
        return this;
      }
      public Builder clearTotalRecord() {
        result.hasTotalRecord = false;
        result.totalRecord_ = 0L;
        return this;
      }
      
      // optional int32 page = 2 [default = 1];
      public boolean hasPage() {
        return result.hasPage();
      }
      public int getPage() {
        return result.getPage();
      }
      public Builder setPage(int value) {
        result.hasPage = true;
        result.page_ = value;
        return this;
      }
      public Builder clearPage() {
        result.hasPage = false;
        result.page_ = 1;
        return this;
      }
      
      // optional int32 perPage = 3 [default = 10];
      public boolean hasPerPage() {
        return result.hasPerPage();
      }
      public int getPerPage() {
        return result.getPerPage();
      }
      public Builder setPerPage(int value) {
        result.hasPerPage = true;
        result.perPage_ = value;
        return this;
      }
      public Builder clearPerPage() {
        result.hasPerPage = false;
        result.perPage_ = 10;
        return this;
      }
      
      // repeated .corner.model.Parameter parameters = 4;
      public java.util.List<corner.model.PaginationProtoBuffer.Parameter> getParametersList() {
        return java.util.Collections.unmodifiableList(result.parameters_);
      }
      public int getParametersCount() {
        return result.getParametersCount();
      }
      public corner.model.PaginationProtoBuffer.Parameter getParameters(int index) {
        return result.getParameters(index);
      }
      public Builder setParameters(int index, corner.model.PaginationProtoBuffer.Parameter value) {
        result.parameters_.set(index, value);
        return this;
      }
      public Builder setParameters(int index, corner.model.PaginationProtoBuffer.Parameter.Builder builderForValue) {
        result.parameters_.set(index, builderForValue.build());
        return this;
      }
      public Builder addParameters(corner.model.PaginationProtoBuffer.Parameter value) {
        if (result.parameters_.isEmpty()) {
          result.parameters_ = new java.util.ArrayList<corner.model.PaginationProtoBuffer.Parameter>();
        }
        result.parameters_.add(value);
        return this;
      }
      public Builder addParameters(corner.model.PaginationProtoBuffer.Parameter.Builder builderForValue) {
        if (result.parameters_.isEmpty()) {
          result.parameters_ = new java.util.ArrayList<corner.model.PaginationProtoBuffer.Parameter>();
        }
        result.parameters_.add(builderForValue.build());
        return this;
      }
      public Builder addAllParameters(
          java.lang.Iterable<? extends corner.model.PaginationProtoBuffer.Parameter> values) {
        if (result.parameters_.isEmpty()) {
          result.parameters_ = new java.util.ArrayList<corner.model.PaginationProtoBuffer.Parameter>();
        }
        super.addAll(values, result.parameters_);
        return this;
      }
      public Builder clearParameters() {
        result.parameters_ = java.util.Collections.emptyList();
        return this;
      }
    }
    
    static {
      corner.model.PaginationProtoBuffer.getDescriptor();
    }
  }
  
  public static final class Parameter extends
      com.google.protobuf.GeneratedMessage {
    // Use Parameter.newBuilder() to construct.
    private Parameter() {}
    
    private static final Parameter defaultInstance = new Parameter();
    public static Parameter getDefaultInstance() {
      return defaultInstance;
    }
    
    public Parameter getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return corner.model.PaginationProtoBuffer.internal_static_corner_model_Parameter_descriptor;
    }
    
    @Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return corner.model.PaginationProtoBuffer.internal_static_corner_model_Parameter_fieldAccessorTable;
    }
    
    // required string key = 1;
    private boolean hasKey;
    private java.lang.String key_ = "";
    public boolean hasKey() { return hasKey; }
    public java.lang.String getKey() { return key_; }
    
    // required string value = 2;
    private boolean hasValue;
    private java.lang.String value_ = "";
    public boolean hasValue() { return hasValue; }
    public java.lang.String getValue() { return value_; }
    
    @Override
    public final boolean isInitialized() {
      if (!hasKey) return false;
      if (!hasValue) return false;
      return true;
    }
    
    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (hasKey()) {
        output.writeString(1, getKey());
      }
      if (hasValue()) {
        output.writeString(2, getValue());
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    @Override
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (hasKey()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(1, getKey());
      }
      if (hasValue()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(2, getValue());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static corner.model.PaginationProtoBuffer.Parameter parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static corner.model.PaginationProtoBuffer.Parameter parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static corner.model.PaginationProtoBuffer.Parameter parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static corner.model.PaginationProtoBuffer.Parameter parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static corner.model.PaginationProtoBuffer.Parameter parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static corner.model.PaginationProtoBuffer.Parameter parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static corner.model.PaginationProtoBuffer.Parameter parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static corner.model.PaginationProtoBuffer.Parameter parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return new Builder(); }
    public Builder newBuilderForType() { return new Builder(); }
    public static Builder newBuilder(corner.model.PaginationProtoBuffer.Parameter prototype) {
      return new Builder().mergeFrom(prototype);
    }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      // Construct using corner.model.PaginationProtoBuffer.Parameter.newBuilder()
      private Builder() {}
      
      corner.model.PaginationProtoBuffer.Parameter result = new corner.model.PaginationProtoBuffer.Parameter();
      
      @Override
      protected corner.model.PaginationProtoBuffer.Parameter internalGetResult() {
        return result;
      }
      
      @Override
      public Builder clear() {
        result = new corner.model.PaginationProtoBuffer.Parameter();
        return this;
      }
      
      @Override
      public Builder clone() {
        return new Builder().mergeFrom(result);
      }
      
      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return corner.model.PaginationProtoBuffer.Parameter.getDescriptor();
      }
      
      public corner.model.PaginationProtoBuffer.Parameter getDefaultInstanceForType() {
        return corner.model.PaginationProtoBuffer.Parameter.getDefaultInstance();
      }
      
      public corner.model.PaginationProtoBuffer.Parameter build() {
        if (!isInitialized()) {
          throw new com.google.protobuf.UninitializedMessageException(
            result);
        }
        return buildPartial();
      }
      
      private corner.model.PaginationProtoBuffer.Parameter buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw new com.google.protobuf.UninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public corner.model.PaginationProtoBuffer.Parameter buildPartial() {
        corner.model.PaginationProtoBuffer.Parameter returnMe = result;
        result = null;
        return returnMe;
      }
      
      @Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof corner.model.PaginationProtoBuffer.Parameter) {
          return mergeFrom((corner.model.PaginationProtoBuffer.Parameter)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(corner.model.PaginationProtoBuffer.Parameter other) {
        if (other == corner.model.PaginationProtoBuffer.Parameter.getDefaultInstance()) return this;
        if (other.hasKey()) {
          setKey(other.getKey());
        }
        if (other.hasValue()) {
          setValue(other.getValue());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input)
          throws java.io.IOException {
        return mergeFrom(input,
          com.google.protobuf.ExtensionRegistry.getEmptyRegistry());
      }
      
      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistry extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10: {
              setKey(input.readString());
              break;
            }
            case 18: {
              setValue(input.readString());
              break;
            }
          }
        }
      }
      
      
      // required string key = 1;
      public boolean hasKey() {
        return result.hasKey();
      }
      public java.lang.String getKey() {
        return result.getKey();
      }
      public Builder setKey(java.lang.String value) {
        result.hasKey = true;
        result.key_ = value;
        return this;
      }
      public Builder clearKey() {
        result.hasKey = false;
        result.key_ = "";
        return this;
      }
      
      // required string value = 2;
      public boolean hasValue() {
        return result.hasValue();
      }
      public java.lang.String getValue() {
        return result.getValue();
      }
      public Builder setValue(java.lang.String value) {
        result.hasValue = true;
        result.value_ = value;
        return this;
      }
      public Builder clearValue() {
        result.hasValue = false;
        result.value_ = "";
        return this;
      }
    }
    
    static {
      corner.model.PaginationProtoBuffer.getDescriptor();
    }
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_corner_model_Pagination_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_corner_model_Pagination_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_corner_model_Parameter_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_corner_model_Parameter_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String descriptorData =
      "\n!corner/model/PaginationBean.proto\022\014cor" +
      "ner.model\"w\n\nPagination\022\026\n\013totalRecord\030\001" +
      " \001(\003:\0010\022\017\n\004page\030\002 \001(\005:\0011\022\023\n\007perPage\030\003 \001(" +
      "\005:\00210\022+\n\nparameters\030\004 \003(\0132\027.corner.model" +
      ".Parameter\"\'\n\tParameter\022\013\n\003key\030\001 \002(\t\022\r\n\005" +
      "value\030\002 \002(\tB\'\n\014corner.modelB\025PaginationP" +
      "rotoBufferH\001";
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_corner_model_Pagination_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_corner_model_Pagination_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_corner_model_Pagination_descriptor,
              new java.lang.String[] { "TotalRecord", "Page", "PerPage", "Parameters", },
              corner.model.PaginationProtoBuffer.Pagination.class,
              corner.model.PaginationProtoBuffer.Pagination.Builder.class);
          internal_static_corner_model_Parameter_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_corner_model_Parameter_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_corner_model_Parameter_descriptor,
              new java.lang.String[] { "Key", "Value", },
              corner.model.PaginationProtoBuffer.Parameter.class,
              corner.model.PaginationProtoBuffer.Parameter.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }
}
