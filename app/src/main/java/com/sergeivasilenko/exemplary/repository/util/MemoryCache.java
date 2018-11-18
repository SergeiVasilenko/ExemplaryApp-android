package com.sergeivasilenko.exemplary.repository.util;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created on 27.07.17.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */

public class MemoryCache<Key, Value> {
	private Map<Key, MutableLiveData<Value>> mMap = new ConcurrentHashMap<>();

	public void put(Key key, Value value) {
		MutableLiveData<Value> data;
		if (mMap.containsKey(key)) {
			data = mMap.get(key);
		} else {
			data = new MutableLiveData<>();
			mMap.put(key, data);
		}
		data.postValue(value);
	}

	@MainThread
	public void putImmediately(Key key, Value value) {
		MutableLiveData<Value> data;
		if (mMap.containsKey(key)) {
			data = mMap.get(key);
		} else {
			data = new MutableLiveData<>();
			mMap.put(key, data);
		}
		data.setValue(value);
	}

	public void putIfAbsent(Key key, Value value) {
		if (!mMap.containsKey(key)) {
			MutableLiveData<Value> data = new MutableLiveData<>();
			mMap.put(key, data);
			data.postValue(value);
		}
	}

	public LiveData<Value> get(@NonNull Key key) {
		return getDefault(key, null);
	}

	public LiveData<Value> getDefault(@NonNull Key key, Value defaultValue) {
		MutableLiveData<Value> data = mMap.get(key);
		if (data == null) {
			// wait for data
			data = new MutableLiveData<>();
			data.postValue(defaultValue);
			mMap.put(key, data);
		}
		return data;
	}

	public Set<Key> getKeys() {
		return mMap.keySet();
	}

	public Collection<MutableLiveData<Value>> values() {
		return mMap.values();
	}

	public Map<Key, Value> getMap() {
		Map<Key, Value> map = new HashMap<>(mMap.size());
		for (Map.Entry<Key, MutableLiveData<Value>> entry: mMap.entrySet()) {
			map.put(entry.getKey(), entry.getValue().getValue());
		}
		return map;
	}

	@MainThread
	public void putMap(Map<Key, Value> map) {
		for (Map.Entry<Key, Value> entry: map.entrySet()) {
			putImmediately(entry.getKey(), entry.getValue());
		}
	}

	public void clear() {
		mMap.clear();
	}

	public void remove(Key key) {
		if (mMap.containsKey(key)) {
			MutableLiveData<Value> data = mMap.get(key);
			data.postValue(null);
		}
	}

	@MainThread
	public void removeImmediately(Key key) {
		if (mMap.containsKey(key)) {
			MutableLiveData<Value> data = mMap.get(key);
			data.setValue(null);
		}
	}
}
