package com.test.alten.productsapi.services;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.Date;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.flextrade.jfixture.FixtureAnnotations;
import com.flextrade.jfixture.JFixture;
import com.flextrade.jfixture.annotations.Fixture;
import com.test.alten.productsapi.feign.SimilarProductsFeignClient;
import com.test.alten.productsapi.model.ProductDetailDto;
import feign.FeignException;
import feign.Request;
import feign.RetryableException;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailServiceTest {

    private ProductDetailService productDetailService;

    @Mock
    private SimilarProductsFeignClient similarProductsFeignClientMock;

    @Fixture
    private ProductDetailDto productDetailDtoMock;

    private JFixture jFixture;

    @Before
    public void init() {
        jFixture = new JFixture();
        jFixture.customise().circularDependencyBehaviour().omitSpecimen();
        FixtureAnnotations.initFixtures(this, jFixture);
        this.productDetailService = new ProductDetailService(similarProductsFeignClientMock);
    }

    @Test
    public void searchProductOk() {
        doReturn(productDetailDtoMock).when(similarProductsFeignClientMock).getProductDetail(Mockito.anyString());
        ProductDetailDto result = this.productDetailService.searchProduct(Mockito.anyString());
        Assert.assertEquals(productDetailDtoMock.getAvailability(), result.getAvailability());
        Assert.assertEquals(productDetailDtoMock.getId(), result.getId());
        Assert.assertEquals(productDetailDtoMock.getName(), result.getName());
        Assert.assertEquals(productDetailDtoMock.getPrice(), result.getPrice());
    }

    @Test
    public void searchProductKo_TimeOutException() {
        doThrow(mockRetryableException()).when(similarProductsFeignClientMock).getProductDetail(Mockito.anyString());
        ProductDetailDto result = this.productDetailService.searchProduct(Mockito.anyString());
        Assert.assertNull(result);
    }

    @Test
    public void searchProductKoFeign_404() {
        doThrow(mock404FeignException()).when(similarProductsFeignClientMock).getProductDetail(Mockito.anyString());
        ProductDetailDto result = this.productDetailService.searchProduct(Mockito.anyString());
        Assert.assertNull(result);
    }

    @Test
    public void searchProductKoFeign_500() {
        doThrow(mock500FeignException()).when(similarProductsFeignClientMock).getProductDetail(Mockito.anyString());
        ProductDetailDto result = this.productDetailService.searchProduct(Mockito.anyString());
        Assert.assertNull(result);
    }

    @Test
    public void searchProductKo_500() {
        doThrow(new RuntimeException()).when(similarProductsFeignClientMock).getProductDetail(Mockito.anyString());
        ProductDetailDto result = this.productDetailService.searchProduct(Mockito.anyString());
        Assert.assertNull(result);
    }


    private RetryableException mockRetryableException() {
        return new RetryableException(0, "TimeOut test", null, new Date(), mockFeignRequest());
    }

    private FeignException mock404FeignException() {
        return new FeignException.NotFound("Not found test", mockFeignRequest(), new byte[0], new HashMap<>());
    }

    private FeignException mock500FeignException() {
        return new FeignException.InternalServerError("Internal Error test", mockFeignRequest(), new byte[0], new HashMap<>());
    }

    private Request mockFeignRequest() {
        return Request.create(Request.HttpMethod.GET, "Http://test.es", new HashMap<>(), mockFeignBody(), null);
    }

    private Request.Body mockFeignBody() {
        return Request.Body.create(new byte[0]);
    }

}
