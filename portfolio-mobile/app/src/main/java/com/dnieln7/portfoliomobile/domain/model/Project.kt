package com.dnieln7.portfoliomobile.domain.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
@Entity(tableName = "tb_projects")
data class Project(
    @Json(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @Json(name = "images")
    val images: List<String>,
    @Json(name = "importance")
    val importance: Double,
    @Json(name = "name")
    val name: String,
    @Json(name = "ownership")
    @ColumnInfo(defaultValue = "N/A")
    val ownership: String,
    @Json(name = "programGit")
    val programGit: String,
    @Json(name = "programUrl")
    val programUrl: String,
    @Json(name = "summary")
    val summary: String,
    @Json(name = "tags")
    val tags: List<String>,
    @Json(name = "technologies")
    val technologies: List<String>,
    @Json(name = "thumbnail")
    val thumbnail: String,
    @Json(name = "updatedAt")
    val updatedAt: String,
    @Json(name = "webGit")
    val webGit: String,
    @Json(name = "webUrl")
    val webUrl: String,
    @Json(name = "year")
    val year: Int,
    @Json(name = "androidGit")
    val androidGit: String,
    @Json(name = "androidUrl")
    val androidUrl: String,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "duration")
    val duration: String,
    @Json(name = "features")
    val features: List<String>,
) : Serializable