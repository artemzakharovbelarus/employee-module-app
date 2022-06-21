package com.azakharov.employeeapp.rest.spark.proxy;

import com.azakharov.employeeapp.rest.util.JsonUtil;
import com.google.common.net.MediaType;
import org.eclipse.jetty.http.HttpStatus;
import spark.Route;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BaseSparkRestController<DTO, V> {

    private static final String DELETE_SUCCESS_MESSAGE_TEMPLATE = "{0} with ID {1} was deleted";

    protected static final String ID_ENDPOINT_PARAM = ":id";

    protected final JsonUtil jsonUtil;

    public BaseSparkRestController(final JsonUtil jsonUtil) {
        this.jsonUtil = jsonUtil;
    }

    protected Route performGetViewEndpoint(final Function<Long, V> controller) {
        return (request, response) -> {

            final var id = Long.valueOf(request.params(ID_ENDPOINT_PARAM));
            final var domain = controller.apply(id);

            response.status(HttpStatus.OK_200);
            response.type(MediaType.JSON_UTF_8.type());

            return jsonUtil.write(domain);
        };
    }

    protected Route performGetAllViewsEndpoint(final Supplier<List<V>> controller) {
        return (request, response) -> {

            final var domains = controller.get();

            response.status(HttpStatus.OK_200);
            response.type(MediaType.JSON_UTF_8.type());

            return jsonUtil.writeAll(domains);
        };
    }

    protected Route performUpsertDomainEndpoint(final Function<DTO, V> controller, Class<DTO> dtoClass) {
        return (request, response) -> {

            final var savingPositionDto = jsonUtil.read(request.body(), dtoClass);
            final var savedPositionView = controller.apply(savingPositionDto);

            response.status(HttpStatus.OK_200);
            response.type(MediaType.JSON_UTF_8.type());

            return jsonUtil.write(savedPositionView);
        };
    }

    protected Route performDeleteDomainEndpoint(final Consumer<Long> controller, String deletingDomainName) {
        return (request, response) -> {

            final var id = Long.valueOf(request.params(ID_ENDPOINT_PARAM));
            controller.accept(id);

            response.status(HttpStatus.OK_200);
            response.type(MediaType.JSON_UTF_8.type());

            return MessageFormat.format(DELETE_SUCCESS_MESSAGE_TEMPLATE, deletingDomainName, id);
        };
    }
}