# springboot-generic-excel-processor

---

* mvn clean package 

* api endpoing 
* http://localhost:8080/process/person
```
{
  "data": {
    "success": [
      {
        "id": 1,
        "personName": "adarsh",
        "email": "adarsh@kumar",
        "phoneNumber": "2"
      },
      {
        "id": 2,
        "personName": "amit",
        "email": "amit@kumar",
        "phoneNumber": "234356"
      },
      {
        "id": 3,
        "personName": "shakti",
        "email": "shakti@singh",
        "phoneNumber": "6"
      },
      {
        "id": 4,
        "personName": "usha",
        "email": "usha@singh",
        "phoneNumber": "6789056"
      },
      {
        "id": 5,
        "personName": "radha",
        "email": "radha@singh",
        "phoneNumber": "4567890"
      }
    ],
    "failed": [
      {
        "id": 6,
        "personName": "sonu",
        "email": null,
        "phoneNumber": "6"
      }
    ]
  },
  "message": "Excel file is processed with failed Data "
}
```

* http://localhost:8080/process/address
```
{
  "data": {
    "success": [
      {
        "addressId": 1,
        "street": "abc",
        "city": "x1",
        "state": "sate1",
        "country": null,
        "zipCode": "country1"
      },
      {
        "addressId": 2,
        "street": "dfg",
        "city": "x2",
        "state": "sate2",
        "country": null,
        "zipCode": "country2"
      },
      {
        "addressId": 3,
        "street": "adg",
        "city": "x3",
        "state": "sate3",
        "country": null,
        "zipCode": "country3"
      },
      {
        "addressId": 4,
        "street": "adr",
        "city": "x4",
        "state": "sate4",
        "country": null,
        "zipCode": "country4"
      },
      {
        "addressId": 5,
        "street": "ggad",
        "city": "x5",
        "state": "sate5",
        "country": null,
        "zipCode": "country5"
      }
    ],
    "failed": []
  },
  "message": "Excel file is processed successfully"
}
```

* http://localhost:8080/process/default
```
{
  "data": {
    "success": [
      {
        "entities": [
          {
            "zipcode": "12345.0",
            "owner": "adarsh",
            "country": "country1",
            "city": "x1",
            "street": "abc",
            "id": "1.0",
            "state": "sate1"
          }
        ]
      },
      {
        "entities": [
          {
            "zipcode": "12345.0",
            "owner": "radha",
            "country": "country2",
            "city": "x2",
            "street": "dfg",
            "id": "2.0",
            "state": "sate2"
          }
        ]
      },
      {
        "entities": [
          {
            "zipcode": "12345.0",
            "owner": "shakti",
            "country": "country3",
            "city": "x3",
            "street": "adg",
            "id": "3.0",
            "state": "sate3"
          }
        ]
      },
      {
        "entities": [
          {
            "zipcode": "12345.0",
            "owner": "usha",
            "country": "country4",
            "city": "x4",
            "street": "adr",
            "id": "4.0",
            "state": "sate4"
          }
        ]
      },
      {
        "entities": [
          {
            "zipcode": "12345.0",
            "owner": "amit",
            "country": "country5",
            "city": "x5",
            "street": "ggad",
            "id": "5.0",
            "state": "sate5"
          }
        ]
      }
    ],
    "failed": []
  },
  "message": "Excel file is processed successfully"
}
```