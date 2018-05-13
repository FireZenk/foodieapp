# foodieapp

In order to build the app you need to have some env variables defined, there is two ways to do it:

## By defining *.properties files

You have to create two files locally at the project's root folder:

`mapbox.properties` with the following content:
```bash
MAPBOX_API_KEY="your_api_key"
```

and `foursquare.properties` with:
```bash
FOURSQUARE_CLIENT_ID="your_client_id"
FOURSQUARE_CLIENT_SECRET="your_client_secret"
```

## By defining environment variables

You have to define in your system the following environment variables:

From your Mapbox developer account:
```bash
MAPBOX_API_KEY="your_api_key"
```

From your Foursquare developer account:
```bash
FOURSQUARE_CLIENT_ID="your_client_id"
FOURSQUARE_CLIENT_SECRET="your_client_secret"
```
