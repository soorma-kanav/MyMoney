package org.mymoney.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mymoney.enums.ErrorCode;
import org.mymoney.exception.MyMoneyException;
import org.mymoney.util.FileUtil;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PortfolioFileProcessorTest {

    private PortfolioFileProcessor portfolioFileProcessor = new PortfolioFileProcessor();

    private String testFilePath;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    class processInputFile{

        @AfterEach
        void tearDown(){
            // DELETE THE FILERESOURCE CREATED IF IT EXISTS in the testFilePath
        }

        private void callProcessInputFile(){
            portfolioFileProcessor.processInputFile(testFilePath);
        }

        @Nested
        class whenFilePathIsNotCorrect{
            @Nested
            class whenFilePathIsNull{

                @BeforeEach
                void setup(){
                    String nullFilePath = FileUtil.createNullFilePath();
                    testFilePath = nullFilePath;
                }

                @Test
                void shouldThrowMyMoneyException(){
                    assertThrows(MyMoneyException.class, () -> callProcessInputFile());
                }

                @Test
                void shouldHaveErrorCodeForFileAccessError(){
                    MyMoneyException myMoneyException = assertThrows(MyMoneyException.class, () -> callProcessInputFile());
                    assertEquals(myMoneyException.getErrorCode(), ErrorCode.FILE_ACCESS_ERROR);
                }
            }
            @Nested
            class whenFilePathDoesNotExist{

                @BeforeEach
                void setup(){
                    testFilePath = "*****RANDOM_FILE_PATH****";
                }

                @Test
                void shouldThrowMyMoneyException(){
                    assertThrows(MyMoneyException.class, () -> callProcessInputFile());
                }

                @Test
                void shouldHaveErrorCodeForFileAccessError(){
                    MyMoneyException myMoneyException = assertThrows(MyMoneyException.class, () -> callProcessInputFile());
                    assertEquals(myMoneyException.getErrorCode(), ErrorCode.FILE_ACCESS_ERROR);
                }
            }
            @Nested
            class whenFileReadAccessIsNotThere{

                @BeforeEach
                void setup(){
                    String noReadAccessFilePath = FileUtil.createNoReadAccessFile();
                    testFilePath = noReadAccessFilePath;
                }

                @Test
                void shouldThrowMyMoneyException(){
                    assertThrows(MyMoneyException.class, () -> callProcessInputFile());
                }

                @Test
                void shouldHaveErrorCodeForFileAccessError(){
                    MyMoneyException myMoneyException = assertThrows(MyMoneyException.class, () -> callProcessInputFile());
                    assertEquals(myMoneyException.getErrorCode(), ErrorCode.FILE_ACCESS_ERROR);
                }
            }
            @Nested
            class whenFileIsEmpty{

                @BeforeEach
                void setup() throws IOException {
                    String emptyFilePath = FileUtil.createEmptyFile();
                    testFilePath = emptyFilePath;
                }

                @Test
                void shouldThrowFileNotFoundException(){
                    assertDoesNotThrow(() -> callProcessInputFile());
                }
            }
        }


    }
}
