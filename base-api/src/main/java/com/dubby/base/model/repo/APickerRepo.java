package com.dubby.base.model.repo;

import ch.lambdaj.function.matcher.LambdaJMatcher;
import com.dubby.base.exception.BaseException;
import com.dubby.base.model.Dictionary;
import com.dubby.base.model.entity.HasPicker;
import org.hamcrest.Matchers;

import java.util.List;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;

public abstract class APickerRepo<T extends HasPicker> extends ACacheContainerRepo<T> implements PickerRepo<T> {

    protected abstract Dictionary getDictionary();

    protected T findByCode(String code, String ... additionalKey) {
        return findOne(having(on(HasPicker.class).getPickerCode(),
                Matchers.equalTo(code)));
    }

    protected LambdaJMatcher constructPickerSearchMatcher(String keyword, String ... additionalKey) {
        return having(on(HasPicker.class).getPickerCode(),
                Matchers.containsString(keyword))
                .or(having(on(HasPicker.class).getPickerDescription(),
                        Matchers.containsString(keyword)));
    }

    @Override
    public List<T> search(String keyword) {
        return search(constructPickerSearchMatcher(keyword));
    }

    @Override
    public List<T> pickerSearch(String keyword, String ... additionalKey) {

        return search(constructPickerSearchMatcher(keyword, additionalKey));
    }

    @Override
    public String findDescription(String code, String... additionalKey) throws BaseException {

        HasPicker instance = findByCode(code, additionalKey);

        if (instance != null) {
            return instance.getPickerDescription();
        } else {
            throw new BaseException(getDictionary().constructString("common.error.picker.invalid.code"));
        }
    }
}
