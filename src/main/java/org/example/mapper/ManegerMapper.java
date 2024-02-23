package org.example.mapper;

public interface ManegerMapper <D, E, DA>{

    E paraEntity (D domain);
    D paraDomain (E entity);
    D paraDomainDeDados (DA dados);
}
