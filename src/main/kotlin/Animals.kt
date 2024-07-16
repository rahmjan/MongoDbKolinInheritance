package org.example

import org.bson.codecs.pojo.annotations.BsonDiscriminator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

// Base class
@BsonDiscriminator
@NoArg
open class Animal(
    @BsonId var id: ObjectId? = null,
    var name: String,
    var type: String
)

// Subclass
class Cat(
    id: ObjectId? = null,
    name: String,
    var favoriteToy: String
) : Animal(id, name, "Cat")

// Subclass
class Dog(
    id: ObjectId? = null,
    name: String,
    var favoritePark: String
) : Animal(id, name, "Dog")