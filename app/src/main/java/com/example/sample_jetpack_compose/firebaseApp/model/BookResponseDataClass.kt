package com.example.sample_jetpack_compose.firebaseApp.model

data class BookResponseDataClass(
    val items: List<BookDetails>,
    val kind: String,
    val totalItems: Int
) {
    data class BookDetails(
        val accessInfo: AccessInfo,
        val etag: String,
        val id: String,
        val kind: String,
        val saleInfo: SaleInfo,
        val searchInfo: SearchInfo,
        val selfLink: String,
        val volumeInfo: VolumeInfo
    ) {
        data class AccessInfo(
            val accessViewStatus: String,
            val country: String,
            val embeddable: Boolean,
            val epub: Epub,
            val pdf: Pdf,
            val publicDomain: Boolean,
            val quoteSharingAllowed: Boolean,
            val textToSpeechPermission: String,
            val viewability: String,
            val webReaderLink: String
        ) {
            data class Epub(
                val acsTokenLink: String,
                val isAvailable: Boolean
            )

            data class Pdf(
                val acsTokenLink: String,
                val isAvailable: Boolean
            )
        }

        data class SaleInfo(
            val buyLink: String,
            val country: String,
            val isEbook: Boolean,
            val listPrice: ListPrice,
            val offers: List<Offer>,
            val retailPrice: RetailPrice,
            val saleability: String
        ) {
            data class ListPrice(
                val amount: Double,
                val currencyCode: String
            )

            data class Offer(
                val finskyOfferType: Int,
                val listPrice: ListPrice,
                val retailPrice: RetailPrice
            ) {
                data class ListPrice(
                    val amountInMicros: Long,
                    val currencyCode: String
                )

                data class RetailPrice(
                    val amountInMicros: Long,
                    val currencyCode: String
                )
            }

            data class RetailPrice(
                val amount: Double,
                val currencyCode: String
            )
        }

        data class SearchInfo(
            val textSnippet: String
        )

        data class VolumeInfo(
            val allowAnonLogging: Boolean,
            val authors: List<String>,
            val averageRating: Int,
            val canonicalVolumeLink: String,
            val categories: List<String>,
            val contentVersion: String,
            val description: String,
            val imageLinks: ImageLinks,
            val industryIdentifiers: List<IndustryIdentifier>,
            val infoLink: String,
            val language: String,
            val maturityRating: String,
            val pageCount: Int,
            val panelizationSummary: PanelizationSummary,
            val previewLink: String,
            val printType: String,
            val publishedDate: String,
            val publisher: String,
            val ratingsCount: Int,
            val readingModes: ReadingModes,
            val subtitle: String,
            val title: String
        ) {
            data class ImageLinks(
                val smallThumbnail: String?,
                val thumbnail: String
            )

            data class IndustryIdentifier(
                val identifier: String,
                val type: String
            )

            data class PanelizationSummary(
                val containsEpubBubbles: Boolean,
                val containsImageBubbles: Boolean
            )

            data class ReadingModes(
                val image: Boolean,
                val text: Boolean
            )
        }
    }
}