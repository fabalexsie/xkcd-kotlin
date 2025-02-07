package de.siebes.fabian.xkcd.helper

class ComicNotFoundException(
    comicNumber: Int?,
    message: String
): Exception() {
}