package com.test.alten.productsapi.services;

import static org.mockito.Mockito.doReturn;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.CollectionUtils;

import com.flextrade.jfixture.FixtureAnnotations;
import com.flextrade.jfixture.JFixture;
import com.flextrade.jfixture.annotations.Fixture;
import com.test.alten.productsapi.feign.SimilarProductsFeignClient;
import com.test.alten.productsapi.model.ProductDetailDto;

@RunWith(MockitoJUnitRunner.class)
public class SimilarProductsServiceTest {


    private SimilarProductsService similarProductsService;

    @Mock
    private ProductDetailService productDetailServiceMock;

    @Mock
    private SimilarProductsFeignClient similarProductsFeignClientMock;

    @Fixture
    private ProductDetailDto productDetailDtoMock;

    @Fixture
    private List<String> idListMock;

    private JFixture jFixture;

    @Before
    public void init() {
        jFixture = new JFixture();
        jFixture.customise().circularDependencyBehaviour().omitSpecimen();
        FixtureAnnotations.initFixtures(this, jFixture);
        this.similarProductsService = new SimilarProductsService(similarProductsFeignClientMock, productDetailServiceMock);
    }

    @Test
    public void getSimilarProductsByIdOkNoData() {
        doReturn(Lists.newArrayList()).when(similarProductsFeignClientMock).getSimilarIds(Mockito.anyString());
        List<ProductDetailDto> result = this.similarProductsService.getSimilarProductsById(Mockito.anyString());
        Assert.assertTrue(CollectionUtils.isEmpty(result));
    }

    @Test
    public void getSimilarProductsByIdOk() {
        doReturn(idListMock).when(similarProductsFeignClientMock).getSimilarIds(Mockito.anyString());
        doReturn(productDetailDtoMock).when(productDetailServiceMock).searchProduct(Mockito.anyString());
        List<ProductDetailDto> result = this.similarProductsService.getSimilarProductsById(Mockito.anyString());
        Assert.assertFalse(CollectionUtils.isEmpty(result));
        Assert.assertEquals(idListMock.size(), result.size());
        Assert.assertEquals(productDetailDtoMock.getAvailability(), result.get(0).getAvailability());
        Assert.assertEquals(productDetailDtoMock.getId(), result.get(0).getId());
        Assert.assertEquals(productDetailDtoMock.getName(), result.get(0).getName());
        Assert.assertEquals(productDetailDtoMock.getPrice(), result.get(0).getPrice());

    }
}
