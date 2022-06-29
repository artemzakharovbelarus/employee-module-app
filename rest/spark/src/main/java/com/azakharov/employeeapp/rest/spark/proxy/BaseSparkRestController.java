package com.azakharov.employeeapp.rest.spark.proxy;

import com.azakharov.employeeapp.rest.util.JsonUtil;
import com.azakharov.employeeapp.rest.view.ExceptionView;
import com.google.common.net.MediaType;
import org.eclipse.jetty.http.HttpStatus;
import spark.Route;
import spark.Spark;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BaseSparkRestController<DTO, V> {

    protected static final String ID_ENDPOINT_PARAM = ":id";

    private static final String DELETE_SUCCESS_MESSAGE_TEMPLATE = "{0} with ID {1} was deleted";
    private static final String PAGE_NOT_FOUND_PATTERN = "*";

    protected final JsonUtil jsonUtil;

    public BaseSparkRestController(final JsonUtil jsonUtil) {
        this.jsonUtil = jsonUtil;
    }

    public void performNotFoundEndpoints() {
        Spark.get(PAGE_NOT_FOUND_PATTERN, performPageNotFoundEndpointLogic());
        Spark.post(PAGE_NOT_FOUND_PATTERN, performPageNotFoundEndpointLogic());
        Spark.delete(PAGE_NOT_FOUND_PATTERN, performPageNotFoundEndpointLogic());
        Spark.put(PAGE_NOT_FOUND_PATTERN, performPageNotFoundEndpointLogic());
        Spark.patch(PAGE_NOT_FOUND_PATTERN, performPageNotFoundEndpointLogic());
    }

    protected Route performGetViewEndpointLogic(final Function<Long, V> controller) {
        return (request, response) -> {

            final var id = Long.valueOf(request.params(ID_ENDPOINT_PARAM));
            final var domain = controller.apply(id);

            response.status(HttpStatus.OK_200);
            response.type(MediaType.JSON_UTF_8.type());

            return jsonUtil.write(domain);
        };
    }

    protected Route performGetAllViewsEndpointLogic(final Supplier<List<V>> controller) {
        return (request, response) -> {

            final var domains = controller.get();

            response.status(HttpStatus.OK_200);
            response.type(MediaType.JSON_UTF_8.type());

            return jsonUtil.writeAll(domains);
        };
    }

    protected Route performUpsertDomainEndpointLogic(final Function<DTO, V> controller, Class<DTO> dtoClass) {
        return (request, response) -> {

            final var savingPositionDto = jsonUtil.read(request.body(), dtoClass);
            final var savedPositionView = controller.apply(savingPositionDto);

            response.status(HttpStatus.OK_200);
            response.type(MediaType.JSON_UTF_8.type());

            return jsonUtil.write(savedPositionView);
        };
    }

    protected Route performDeleteDomainEndpointLogic(final Consumer<Long> controller, String deletingDomainName) {
        return (request, response) -> {

            final var id = Long.valueOf(request.params(ID_ENDPOINT_PARAM));
            controller.accept(id);

            response.status(HttpStatus.OK_200);
            response.type(MediaType.JSON_UTF_8.type());

            return MessageFormat.format(DELETE_SUCCESS_MESSAGE_TEMPLATE, deletingDomainName, id);
        };
    }

    private Route performPageNotFoundEndpointLogic() {
        return (request, response) -> {
            response.status(HttpStatus.NOT_FOUND_404);

            final var exceptionView = new ExceptionView(HttpStatus.NOT_FOUND_404, "Page not found");
            return jsonUtil.write(exceptionView);
        };
    }
}