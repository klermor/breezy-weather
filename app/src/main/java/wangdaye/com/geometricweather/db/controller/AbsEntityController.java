package wangdaye.com.geometricweather.db.controller;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import wangdaye.com.geometricweather.db.entity.DaoSession;

public abstract class AbsEntityController<E> {

    protected List<E> getNonNullList(@Nullable List<E> list) {
        return list == null ? new ArrayList<>() : list;
    }
}
