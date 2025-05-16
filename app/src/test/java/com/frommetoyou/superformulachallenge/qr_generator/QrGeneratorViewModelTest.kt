package com.frommetoyou.superformulachallenge.qr_generator

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.frommetoyou.common.model.QrModel
import com.frommetoyou.common.util.Result
import com.frommetoyou.domain.repository.QrRepository
import com.frommetoyou.domain.use_case.QrCodeUseCase
import com.frommetoyou.presentation.QRGeneratorUiState
import com.frommetoyou.presentation.QRGeneratorViewModel
import com.frommetoyou.superformulachallenge.qr_generator.repository.MainCoroutineExtension
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class QrGeneratorViewModelTest {

    private lateinit var viewModel: QRGeneratorViewModel
    private lateinit var qrCodeUseCase: QrCodeUseCase

    private val mockedRepository: QrRepository = mockk()

    companion object {
        @OptIn(ExperimentalCoroutinesApi::class)
        @JvmField
        @RegisterExtension
        val mainCoroutineExtension = MainCoroutineExtension()
    }

    @BeforeEach
    fun setUp() {
        qrCodeUseCase = QrCodeUseCase(mockedRepository)
        viewModel = QRGeneratorViewModel(
            qrCodeUseCase = qrCodeUseCase,
        )
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `viewModel returns a Success uiState when the repository returns a valid response`() =
        runTest {
            withContext(Dispatchers.Default) {
                val qrModel = QrModel.getDefaultModel()

                coEvery { mockedRepository.getQrCode() } returns flow {
                    emit(Result.Loading)
                    emit(Result.Success(qrModel))
                }

                viewModel.qrGeneratorUiState.test {
                    viewModel.getQrCode()

                    val emission1 = awaitItem()
                    assertThat(emission1).isEqualTo(QRGeneratorUiState.Nothing)
                    val emission2 = awaitItem()
                    assertThat(emission2).isEqualTo(QRGeneratorUiState.Loading)
                    val emission3 = awaitItem()
                    assertThat(emission3).isEqualTo(QRGeneratorUiState.Success(
                        qrModel))
                    cancelAndConsumeRemainingEvents()

                }
            }
        }

    @Test
    fun `viewModel returns an Error uiState when the repository returns a failed response, showing mockked approach`() =
        runTest {
            coEvery { mockedRepository.getQrCode() }  returns flow {
                emit(Result.Error(Exception("error")))
            }

            qrCodeUseCase = QrCodeUseCase(mockedRepository)
            viewModel = QRGeneratorViewModel(
                qrCodeUseCase = qrCodeUseCase,
            )

            withContext(Dispatchers.Default) {
                viewModel.qrGeneratorUiState.test {
                    viewModel.getQrCode()

                    val emission1 = awaitItem()
                    assertThat(emission1).isEqualTo(QRGeneratorUiState.Nothing)
                    val emission2 = awaitItem()
                    assertThat(emission2).isEqualTo(QRGeneratorUiState.Error)
                    cancelAndConsumeRemainingEvents()
                }
            }
        }
}