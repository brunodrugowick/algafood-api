The pattern on this package is explained [here](https://blog.scottlogic.com/2020/01/03/rethinking-the-java-dto.html).

Reference code:

```
public enum ProductDTO {;
    private interface Id { @Positive Long getId(); }
    private interface Name { @NotBlank String getName(); }
    private interface Price { @Positive Double getPrice(); }
    private interface Cost { @Positive Double getCost(); }

    public enum Request{;
        @Value public static class Create implements Name, Price, Cost {
            String name;
            Double price;
            Double cost;
        }
    }

    public enum Response{;
        @Value public static class Public implements Id, Name, Price {
            Long id;
            String name;
            Double price;
        }

        @Value public static class Private implements Id, Name, Price, Cost {
            Long id;
            String name;
            Double price;
            Double cost;
        }
    }
}
```
