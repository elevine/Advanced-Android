package com.elevine.twitter.widget;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Adapter that is backed by an in memory List. Find the lastest version at: https://gist.github.com/954463
 * 
 * @author Eric Levine <levine.eri@gmail.com>
 *
 * @param <T> - the type of each Object in the List backing this adapter
 * @param <V> - the type to be used as a view holder.  Must have a no argument constructor 
 */
public abstract class ListAdapter<T, V> extends BaseAdapter{
	private List<T> items =null;
	private LayoutInflater inflator = null;
	private int layoutId;
	
	/**
	 * 
	 * @param context
	 * @param entries - the data backing this adapter
	 * @param layoutId = the R value of the layout which contains the entire View to use for each row
	 */
	public ListAdapter(Context context, List<T> items, int layoutId){
		this.items = items;
		this.inflator =  LayoutInflater.from(context);
		this.layoutId = layoutId;
	}
	
	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public LayoutInflater getInflator() {
		return inflator;
	}

	public void setInflator(LayoutInflater inflator) {
		this.inflator = inflator;
	}



	@Override
	public int getCount() {
		return items.size(); 
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		V holder;

        if (convertView == null) {
        	convertView = inflator.inflate(layoutId, null);
        	holder = createViewHolder();
        	populatViewHolder(holder, convertView);
    
            convertView.setTag(holder);
        }
        else {
            holder = (V) convertView.getTag();
        }
        
        T item = items.get(position);
        setupViews(item, holder);
        
		return convertView;
	}
	
	
	/**
	 * Create a new instance of an object that will hold 
	 * onto inflated views for each row in the list
	 * 
	 * @return
	 */
	public abstract V createViewHolder();
	
	
	/**
	 * Find sub-Views from convertView and add them to the parameter holder instance 
	 */
	 public abstract void populatViewHolder(V holder, View convertView);
	 
	 /**
	  * Pull out values from the parameter item object and set the values 
	  * in the corresponding Views of the parameter holder object
	  * 
	  * @param item
	  */
	 public abstract void setupViews(T item, V holder);
	
	
	
}