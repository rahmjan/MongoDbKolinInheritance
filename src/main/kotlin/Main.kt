package org.example

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.UuidRepresentation
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.kotlin.DataClassCodecProvider
import org.bson.codecs.pojo.Conventions.DEFAULT_CONVENTIONS
import org.bson.codecs.pojo.PojoCodecProvider

fun main() {
    // Create a CodecRegistry for POJOs
    val getCodecRegistry = fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(
                DataClassCodecProvider(),
                PojoCodecProvider.builder()
                    .conventions(DEFAULT_CONVENTIONS) // @BsonDiscriminator
                    .automatic(true)
                    .build(),
            ),
        )
    val getClientSettings =
        MongoClientSettings.builder()
            .uuidRepresentation(UuidRepresentation.STANDARD)
            .applyConnectionString(ConnectionString("mongodb://admin:admin@localhost:27017/"))
            .codecRegistry(getCodecRegistry)
            .build()

    // Connect to MongoDB
    val client = MongoClients.create(getClientSettings)
    val database: MongoDatabase = client.getDatabase("animalDB")
    val collection: MongoCollection<Animal> = database.getCollection("animals", Animal::class.java)

    // Create instances of Cat and Dog
    val cat = Cat(name = "Whiskers", favoriteToy = "Ball")
    val dog = Dog(name = "Buddy", favoritePark = "Central Park")

    // Insert instances into the collection
    collection.insertOne(cat)
    collection.insertOne(dog)

    println("Inserted Cat: $cat")
    println("Inserted Dog: $dog")

    // Fetch all animals
    val animals = collection.find().toList()

    // Print all animals
    println("All animals in the database:")
    animals.forEach { animal ->
        when (animal) {
            is Cat -> println("Cat: ${animal.name}, Favorite Toy: ${animal.favoriteToy}")
            is Dog -> println("Dog: ${animal.name}, Favorite Park: ${animal.favoritePark}")
            else -> println("Unknown animal type: ${animal.name}")
        }
    }
}