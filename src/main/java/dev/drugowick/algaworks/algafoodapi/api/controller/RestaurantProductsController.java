package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericInputDisassembler;
import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.model.ProductModel;
import dev.drugowick.algaworks.algafoodapi.api.model.input.ProductInput;
import dev.drugowick.algaworks.algafoodapi.domain.model.Product;
import dev.drugowick.algaworks.algafoodapi.domain.repository.ProductRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.ProductCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
public class RestaurantProductsController {

	private final ProductRepository productRepository;
	private final ProductCrudService productCrudService;
	private final GenericModelAssembler<Product, ProductModel> modelAssembler;
	private final GenericInputDisassembler<ProductInput, Product> inputDisassembler;

	public RestaurantProductsController(ProductRepository productRepository,
										ProductCrudService productCrudService,
										GenericModelAssembler<Product, ProductModel> modelAssembler,
										GenericInputDisassembler<ProductInput, Product> inputDisassembler) {
		this.productRepository = productRepository;
		this.productCrudService = productCrudService;
		this.modelAssembler = modelAssembler;
		this.inputDisassembler = inputDisassembler;
	}

	@GetMapping
	public List<ProductModel> list(@PathVariable Long restaurantId, @RequestParam(required = false) boolean includeDeactivated) {
		List<Product> queryResult = null;

		if (includeDeactivated) {
			queryResult = productRepository.findByRestaurantId(restaurantId);
		} else {
			queryResult = productRepository.findActiveByRestaurantId(restaurantId);
		}

		return modelAssembler.toCollectionModel(queryResult, ProductModel.class);
	}

	@GetMapping("/{productId}")
	public ProductModel get(@PathVariable Long restaurantId, @PathVariable Long productId) {
		Product product = productCrudService.findOrElseThrow(restaurantId, productId);

		return modelAssembler.toModel(
				product,
				ProductModel.class
		);
	}

	@PutMapping("/{productId}")
	public ProductModel deleteProduct(@PathVariable Long restaurantId,
							  @RequestBody @Valid ProductInput productInput,
							  @PathVariable Long productId) {
		Product productToUpdate = productCrudService.findOrElseThrow(restaurantId, productId);

		inputDisassembler.copyToDomainObject(productInput, productToUpdate);
		return modelAssembler.toModel(productCrudService.save(restaurantId, productToUpdate), ProductModel.class);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductModel createProduct(@PathVariable Long restaurantId, @RequestBody @Valid ProductInput productInput) {
		return modelAssembler.toModel(
				productCrudService.save(restaurantId, inputDisassembler.toDomain(productInput, Product.class)),
				ProductModel.class);
	}

}
