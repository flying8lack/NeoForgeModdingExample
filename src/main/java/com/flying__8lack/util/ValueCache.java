package com.flying__8lack.util;



public class ValueCache<T> {

    private T cache;
    private int old_hash;



    public static <T> ValueCache<T> of(T value){
        return new ValueCache<>(value);
    }

    public T get(){
        return this.cache;
    }
    
    public boolean changed(){
        return this.cache.hashCode() != this.old_hash;
    }

    public void set(T input){
        if(input.hashCode() != this.old_hash){
            this.cache = input;
            this.old_hash = this.cache.hashCode();
        }
    }

    public void Invalidate(){
        this.cache = null;
    }



    public ValueCache(T value){
        this.cache = value;
        this.old_hash = this.cache.hashCode();
    }



}
