package com.example.m03_bounce;

import java.util.List;

public interface DB_Interface {

    public int count();

    public void save(DataModel dataModel);

    public int update(DataModel dataModel);

    public int deleteById(Long id);  // Not implemented

    public List<DataModel> findAll();

    public String getNameById(Long id);  // Not implemented

}
