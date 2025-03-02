# sale_and_purchase

user side implimentation
1. address set to default 
2. wishlist 
3. article edit and show (comment , reply,)
4. change password with email 
5. required settings 
6. dashbaord 

admin side 
1. dashbaord 
2. article update with image
3. show userwise articles
4. logo update
5. banner update
6. admin profile
7. required setting 

home side
1. article wishlist add, thum up ,and thump down icon 
2. article page star rating 
3.  new post page (done)

#table for session stor
CREATE TABLE SPRING_SESSION (
    PRIMARY_ID CHAR(36) NOT NULL,
    SESSION_ID CHAR(36) NOT NULL,
    CREATION_TIME BIGINT NOT NULL,
    LAST_ACCESS_TIME BIGINT NOT NULL,
    MAX_INACTIVE_INTERVAL INT NOT NULL,
    EXPIRY_TIME BIGINT NOT NULL,
    PRINCIPAL_NAME VARCHAR(100),
    PRIMARY KEY (PRIMARY_ID)
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
    SESSION_PRIMARY_ID CHAR(36) NOT NULL,
    ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
    ATTRIBUTE_BYTES BLOB NOT NULL,
    PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION (PRIMARY_ID) ON DELETE CASCADE
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

CREATE TABLE persistent_logins (
    username VARCHAR(64) NOT NULL,
    series VARCHAR(64) PRIMARY KEY,
    token VARCHAR(64) NOT NULL,
    last_used TIMESTAMP NOT NULL
);
 
# implemented 
user: signin,signup,role-base,user-account,edit-post,create-newpost,









# for e-commerce implementation


{
    "orderId": "ORDER12345",
    "userId": 1,
    "customerName": "John Doe",
    "itemDta": "[{\"itemName\":\"Item1\",\"quantity\":2},{\"itemName\":\"Item2\",\"quantity\":1}]",
    "billing": "{\"address\":\"123 Main St\",\"city\":\"Anytown\",\"state\":\"CA\",\"zip\":\"12345\"}",
    "shipping": "{\"address\":\"123 Main St\",\"city\":\"Anytown\",\"state\":\"CA\",\"zip\":\"12345\"}",
    "vouchers": "[{\"code\":\"DISCOUNT10\",\"amount\":10.0}]",
    "gst": 18,
    "voucherDiscount": 10.0,
    "handlingFee": 5.0,
    "processingFee": 2.0,
    "surgeFee": 1.0,
    "deliveryFee": 3.0,
    "totalPrice": 100.0,
    "grandTotal": 119.0,
    "status": "PENDING"
}


for flutter 
dependencies:
  flutter:
    sdk: flutter
  http: ^0.13.3
  cached_network_image: ^3.2.0


import 'package:flutter/material.dart';
import 'package:cached_network_image/cached_network_image.dart';

class ImageScreen extends StatelessWidget {
  final String filename;

  ImageScreen({required this.filename});

  @override
  Widget build(BuildContext context) {
    // Construct the image URL using the filename
    final String imageUrl = 'http://localhost:8080/api/images/$filename';

    return Scaffold(
      appBar: AppBar(
        title: Text('Image Display'),
      ),
      body: Center(
        child: CachedNetworkImage(
          imageUrl: imageUrl,
          placeholder: (context, url) => CircularProgressIndicator(),
          errorWidget: (context, url, error) => Icon(Icons.error),
        ),
      ),
    );
  }
}


#wishlist
id
collection string,
items string "[]"//json
