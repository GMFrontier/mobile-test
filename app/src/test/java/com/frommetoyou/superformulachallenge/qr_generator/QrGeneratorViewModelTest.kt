package com.frommetoyou.superformulachallenge.qr_generator

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.frommetoyou.domain.repository.QrRepository
import com.frommetoyou.domain.use_case.QrCodeUseCase
import com.frommetoyou.presentation.QRGeneratorUiState
import com.frommetoyou.presentation.QRGeneratorViewModel
import com.frommetoyou.superformulachallenge.qr_generator.repository.QrRepositoryFake
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class QrGeneratorViewModelTest {

    private lateinit var viewModel: QRGeneratorViewModel
    private lateinit var qrCodeUseCase: QrCodeUseCase
    private lateinit var qrRepository: QrRepository

    @BeforeEach
    fun setUp() {
        qrRepository = QrRepositoryFake()
        qrCodeUseCase = QrCodeUseCase(qrRepository)
        viewModel = QRGeneratorViewModel(
            qrCodeUseCase = qrCodeUseCase
        )
    }

    @Test
    fun `viewModel returns a Success uiState when the repository returns a valid response`() =
        runBlocking {
            val qrModel = qrRepository.getQrCode().last()
            viewModel.qrGeneratorUiState.test {
                viewModel.getQrCode()

                var item = awaitItem()
                assertThat(item).isEqualTo(QRGeneratorUiState.Loading)
                item = awaitItem()
                assertThat(item).isEqualTo(QRGeneratorUiState.Success(qrModel))
                cancelAndConsumeRemainingEvents()

            }

        }

}