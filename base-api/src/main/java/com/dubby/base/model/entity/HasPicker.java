package com.dubby.base.model.entity;

public interface HasPicker extends HasCache, HasSerializableEntity {
    public String getPickerCode();
    public String getPickerDescription();
}