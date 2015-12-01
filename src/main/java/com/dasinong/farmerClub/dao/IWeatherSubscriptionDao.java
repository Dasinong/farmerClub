package com.dasinong.farmerClub.dao;

import java.util.List;

import com.dasinong.farmerClub.exceptions.WeatherAlreadySubscribedException;
import com.dasinong.farmerClub.model.Field;
import com.dasinong.farmerClub.model.WeatherSubscription;

public interface IWeatherSubscriptionDao extends IEntityDao<WeatherSubscription> {

	public abstract List<WeatherSubscription> findByUserId(Long userId);

	public abstract void updateOrdering(Long[] id);

	WeatherSubscription findByLocationIdAndUserId(Long userId, Long locationId);
}
