package com.shudss00.gigachat.domain.streams

import com.shudss00.gigachat.domain.model.Stream
import io.reactivex.Single
import javax.inject.Inject

class GetAllStreamsUseCase @Inject constructor(
    private val streamRepository: StreamRepository
) {
    operator fun invoke(): Single<List<Stream>> {
        return streamRepository.getAllStreams()
    }
}