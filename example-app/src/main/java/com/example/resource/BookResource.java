package com.example.resource;

import com.example.entity.mongo.Book;
import com.example.exception.ServiceException;
import com.example.repository.SequenceBuilder;
import com.example.service.BookService;

import org.springframework.data.domain.Page;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by binglin on 2016/9/15.
 */

@Path("books")
public class BookResource {

    @Inject
    private BookService bookService;

    @QueryParam("book")
    private Book book;

    @Context
    private UriInfo uriInfo;

    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response save(Book book) {
        book.setId(SequenceBuilder.builder("book"));
        bookService.save(book);
        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI location = ub.path("" + book.getId()).build();
        return Response.created(location).entity(book).build();
    }


    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> get(@PathParam("id") Long id) throws ServiceException {
        Book book = null;
        book = bookService.get(id);
        Map<String, Object> map = new HashMap<>();
        map.put("book", book);
        map.put("status", "200 ok");
        return map;
    }

    @Path("{id}")
    @POST
    public String updateById(@PathParam("id") Long id, @MatrixParam("name") String name) throws ServiceException {
        Book book = bookService.get(id);
        book.setName(name);
        bookService.save(book);
        return "success";
    }


    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Page<Book> find(@QueryParam("name") String name, @QueryParam("page") @DefaultValue("0") Integer page,
                           @QueryParam("size") @DefaultValue("2") Integer size, @MatrixParam("sort") String sort,
                           @MatrixParam("type") String sortType) {
        System.out.println(sort + ":" + sortType);
        Page<Book> books = null;
        if (null == name) {
            books = bookService.listAll();
        } else {
            books = bookService.findByName(name, page, size);
        }
        return books;
    }

    @Path("{id}")
    @DELETE
    public Response delete(@PathParam("id") Integer id) {
        bookService.deleteById(id);
        return Response.noContent().build();
    }
}
