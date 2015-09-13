package com.dubby.base.model.repo;

import com.dubby.base.exception.BaseException;
import com.dubby.base.model.entity.HasPicker;

import java.util.List;

public interface PickerRepo<T extends HasPicker> {

    public List<T> pickerSearch(String keyword, String... additionalKey);
    public String findDescription(String code, String... additionalKey) throws BaseException;
}
