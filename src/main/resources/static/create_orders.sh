#!/bin/bash

count=0
product=1
for restaurant in {1..4}
do
  curl --request PUT --url http://localhost:8080/restaurants/${restaurant}/payment-methods/1
  echo
  for order in {1..100}
  do
    curl --request POST \
    --url http://localhost:8080/orders \
    --header 'content-type: application/json' \
    --data '{
    "restaurant": {
      "id": '${restaurant}'
    },
    "paymentMethod": {
      "id": 1
    },
    "deliveryAddress": {
      "postalCode": "123",
      "city": {
        "id": 1
      },
      "addressLine_1": "Sítio do Pica-Pau",
      "region": "Amarelo"
    },
    "items": [
      {
        "productId": '${restaurant}',
        "amount": '${order}',
        "notes": "Sem salsicha"
      },
      {
        "productId": '${restaurant}',
        "amount": '${order}'
      }
    ]
  }'
    echo
    ((count++))
  done

  echo
  echo "Done with restaurant ${restaurant}"
done

echo
echo "Created ${count} orders for ${restaurant} different restaurants"
