package com.test.alten.productsapi.controllers;

import static org.mockito.Mockito.doReturn;

import com.flextrade.jfixture.FixtureAnnotations;
import com.flextrade.jfixture.JFixture;
import com.flextrade.jfixture.annotations.Fixture;
import com.test.alten.productsapi.model.ProductDetailDto;
import com.test.alten.productsapi.services.SimilarProductsService;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class SimilarProductsControllerTest {

    private SimilarProductsController similarProductsController;

    @Mock
    private SimilarProductsService similarProductsServiceMock;

    @Fixture
    private ProductDetailDto productDetailDtoMock;

    private JFixture jFixture;

    @Before
    public void init() {
        jFixture = new JFixture();
        jFixture.customise().circularDependencyBehaviour().omitSpecimen();
        FixtureAnnotations.initFixtures(this, jFixture);
        this.similarProductsController = new SimilarProductsController(similarProductsServiceMock);
    }

    @Test
    public void getProductSimilar_200(){
        doReturn(Lists.newArrayList(productDetailDtoMock)).when(similarProductsServiceMock).getSimilarProductsById(Mockito.anyString());
        ResponseEntity<List<ProductDetailDto>> result = this.similarProductsController.getProductSimilar(Mockito.anyString());
        Assert.assertEquals(HttpStatus.OK,result.getStatusCode());
        Assert.assertEquals(1,result.getBody().size());
        Assert.assertEquals(productDetailDtoMock.getAvailability(),result.getBody().get(0).getAvailability());
        Assert.assertEquals(productDetailDtoMock.getId(),result.getBody().get(0).getId());
        Assert.assertEquals(productDetailDtoMock.getName(),result.getBody().get(0).getName());
        Assert.assertEquals(productDetailDtoMock.getPrice(),result.getBody().get(0).getPrice());
    }

    @Test
    public void getProductSimilar_400(){
        doReturn(Lists.newArrayList()).when(similarProductsServiceMock).getSimilarProductsById(Mockito.anyString());
        ResponseEntity<List<ProductDetailDto>> result = this.similarProductsController.getProductSimilar(Mockito.anyString());
        Assert.assertEquals(HttpStatus.NOT_FOUND,result.getStatusCode());
    }
}
