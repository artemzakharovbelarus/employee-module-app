package com.azakharov.employeeapp.util.json.json;

import java.io.InputStream;
import java.util.List;

public interface JsonUtil {

    String write(Object dto);
    String writeAll(List<?> dtos);
    <DTO> DTO read(String json, Class<DTO> dtoClass);
    <DTO> List<DTO> readAll(InputStream json, Class<DTO> dtoClass);
}
