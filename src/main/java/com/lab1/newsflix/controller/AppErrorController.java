package com.lab1.newsflix.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Controller
@RequestMapping("/error")
@EnableConfigurationProperties({ServerProperties.class})
public class AppErrorController implements ErrorController {

    private ErrorAttributes errorAttributes;

    @Autowired
    private ServerProperties serverProperties;

    /**
     * ExceptionController
     *
     * @param errorAttributes
     */
    @Autowired
    public AppErrorController(ErrorAttributes errorAttributes) {
        Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/404")
    public ModelAndView errorHtml404(HttpServletRequest request, HttpServletResponse response, WebRequest webRequest) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        Map<String, Object> model = getErrorAttributes(webRequest, isIncludeStackTrace(request, MediaType.TEXT_HTML));

        return new ModelAndView("error/404", model);
    }

    @RequestMapping("/403")
    public ModelAndView errorHtml403(HttpServletRequest request, HttpServletResponse response, WebRequest webRequest) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        Map<String, Object> model = getErrorAttributes(webRequest, isIncludeStackTrace(request, MediaType.TEXT_HTML));
        if (!String.valueOf(model.get("path")).contains(".")) {
            model.put("status", HttpStatus.FORBIDDEN.value());
        }
        return new ModelAndView("error/403", model);
    }

    @RequestMapping("/400")
    public ModelAndView errorHtml400(HttpServletRequest request, HttpServletResponse response, WebRequest webRequest) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        Map<String, Object> model = getErrorAttributes(webRequest, isIncludeStackTrace(request, MediaType.TEXT_HTML));
        return new ModelAndView("error/400", model);
    }

    @RequestMapping("/401")
    public ModelAndView errorHtml401(HttpServletRequest request, HttpServletResponse response, WebRequest webRequest) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> model = getErrorAttributes(webRequest, isIncludeStackTrace(request, MediaType.TEXT_HTML));
        return new ModelAndView("error/401", model);
    }

    @RequestMapping("/500")
    public ModelAndView errorHtml500(HttpServletRequest request, HttpServletResponse response, WebRequest webRequest) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        Map<String, Object> model = getErrorAttributes(webRequest, isIncludeStackTrace(request, MediaType.TEXT_HTML));
        return new ModelAndView("error/500", model);
    }

    /**
     * Determine if the stacktrace attribute should be included.
     *
     * @param request  the source request
     * @param produces the media type produced (or {@code MediaType.ALL})
     * @return if the stacktrace attribute should be included
     */
    protected boolean isIncludeStackTrace(HttpServletRequest request,
                                          MediaType produces) {
        ErrorProperties.IncludeStacktrace include = this.serverProperties.getError().getIncludeStacktrace();
        if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
            return true;
        }
        return include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM && getTraceParameter(request);
    }


    /**
     *
     *
     * @param webRequest
     * @param includeStackTrace
     * @return
     */
    private Map<String, Object> getErrorAttributes(WebRequest webRequest,
                                                   boolean includeStackTrace) {
        return this.errorAttributes.getErrorAttributes(webRequest, includeStackTrace);
    }

    /**
     * trace
     *
     * @param request
     * @return
     */
    private boolean getTraceParameter(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        return parameter != null && !"false".equalsIgnoreCase(parameter);
    }

    /**
     *
     *
     * @param request
     * @return
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    /**
     *      *
     * @return
     */
    @Override
    public String getErrorPath() {
        return "";
    }}