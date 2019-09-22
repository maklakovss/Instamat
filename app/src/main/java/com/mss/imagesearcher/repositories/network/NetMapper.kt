package com.mss.imagesearcher.repositories.network

import com.mss.imagesearcher.domain.models.ImageInfo
import com.mss.imagesearcher.repositories.network.models.ImageInfoNet
import java.util.*

object NetMapper {

    fun mapFromNet(imagesNet: List<ImageInfoNet>): List<ImageInfo> {
        val images = ArrayList<ImageInfo>()
        for (imageNet in imagesNet) {
            images.add(mapFromNet(imageNet))
        }
        return images
    }

    private fun mapFromNet(imageInfoDB: ImageInfoNet) = ImageInfo(
            imageInfoDB.id,
            imageInfoDB.largeImageURL,
            imageInfoDB.previewURL,
            imageInfoDB.webFormatURL,
            imageInfoDB.height,
            imageInfoDB.width,
            imageInfoDB.type,
            imageInfoDB.tags,
            imageInfoDB.pageUrl,
            imageInfoDB.likes,
            imageInfoDB.views,
            imageInfoDB.comments
    )
}
