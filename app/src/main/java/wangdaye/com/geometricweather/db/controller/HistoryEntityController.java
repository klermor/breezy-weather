package wangdaye.com.geometricweather.db.controller;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import wangdaye.com.geometricweather.basic.model.option.provider.WeatherSource;
import wangdaye.com.geometricweather.db.entity.DaoSession;
import wangdaye.com.geometricweather.db.entity.HistoryEntity;
import wangdaye.com.geometricweather.db.entity.HistoryEntityDao;
import wangdaye.com.geometricweather.db.propertyConverter.WeatherSourceConverter;

public class HistoryEntityController extends AbsEntityController<HistoryEntity> {

    // insert.

    public void insertHistoryEntity(@NonNull DaoSession session, @NonNull HistoryEntity entity) {
        session.getHistoryEntityDao().insert(entity);
    }

    // delete.

    public void deleteLocationHistoryEntity(@NonNull DaoSession session,
                                            @NonNull List<HistoryEntity> entityList) {
        session.getHistoryEntityDao().deleteInTx(entityList);
    }

    // select.

    @SuppressLint("SimpleDateFormat")
    @Nullable
    public HistoryEntity selectYesterdayHistoryEntity(@NonNull DaoSession session,
                                                      @NonNull String cityId, @NonNull WeatherSource source,
                                                      @NonNull Date currentDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date today = format.parse(format.format(currentDate));
            if (today == null) {
                throw new NullPointerException("Get null Date object.");
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            calendar.add(Calendar.DATE, -1);
            Date yesterday = calendar.getTime();

            List<HistoryEntity> entityList = session.getHistoryEntityDao()
                    .queryBuilder()
                    .where(
                            HistoryEntityDao.Properties.Date.ge(yesterday),
                            HistoryEntityDao.Properties.Date.lt(today),
                            HistoryEntityDao.Properties.CityId.eq(cityId),
                            HistoryEntityDao.Properties.WeatherSource.eq(
                                    new WeatherSourceConverter().convertToDatabaseValue(source)
                            )
                    ).list();

            if (entityList == null || entityList.size() == 0) {
                return null;
            } else {
                return entityList.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressLint("SimpleDateFormat")
    @Nullable
    private HistoryEntity selectTodayHistoryEntity(@NonNull DaoSession session,
                                                   @NonNull String cityId, @NonNull WeatherSource source,
                                                   @NonNull Date currentDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date today = format.parse(format.format(currentDate));
            if (today == null) {
                throw new NullPointerException("Get null Date object.");
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            calendar.add(Calendar.DATE, +1);
            Date tomorrow = calendar.getTime();

            List<HistoryEntity> entityList = session.getHistoryEntityDao()
                    .queryBuilder()
                    .where(
                            HistoryEntityDao.Properties.Date.ge(today),
                            HistoryEntityDao.Properties.Date.lt(tomorrow),
                            HistoryEntityDao.Properties.CityId.eq(cityId),
                            HistoryEntityDao.Properties.WeatherSource.eq(
                                    new WeatherSourceConverter().convertToDatabaseValue(source)
                            )
                    ).list();
            if (entityList == null || entityList.size() == 0) {
                return null;
            } else {
                return entityList.get(0);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @NonNull
    public List<HistoryEntity> selectHistoryEntityList(@NonNull DaoSession session,
                                                       @NonNull String cityId, @NonNull WeatherSource source) {
        return getNonNullList(
                session.getHistoryEntityDao()
                        .queryBuilder()
                        .where(
                                HistoryEntityDao.Properties.CityId.eq(cityId),
                                HistoryEntityDao.Properties.WeatherSource.eq(
                                        new WeatherSourceConverter().convertToDatabaseValue(source)
                                )
                        ).list()
        );
    }
}
