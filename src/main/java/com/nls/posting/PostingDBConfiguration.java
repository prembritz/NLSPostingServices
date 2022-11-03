package com.nls.posting;

import com.mchange.v2.c3p0.DataSources;
import com.mchange.v2.c3p0.PooledDataSource;
import com.mchange.v2.log.MLevel;
import com.nls.core.BatchCoreServices;
import com.nls.core.CoreServices;
import com.nls.core.MobileAirtimeCoreService;
import com.nls.core.MobileMoneyCoreService;
import com.nls.core.PesaLinkCoreService;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

@ApplicationPath("/Posting")
@OpenAPIDefinition(info = @Info(title = "Posting API", version = "1.0.0"))
public class PostingDBConfiguration extends Application {
  // @ConfigProperties
  Config configProperties;

  private static String coreSchema = "";
  private static String channelSchema = "";
  private static DataSource cmDBPool;
  private static HashMap<String, String> ActualTableName;
  private static HashMap<String, String> GlobalParameters;

  private static long timeoutOutValue;
  private static long readtimeoutOutValue;
  private static CoreServices coreServices;
  private static BatchCoreServices BatchcoreServices;
  private static PesaLinkCoreService PesalinkcoreServices;
  private static MobileMoneyCoreService MobMoneycoreServices;
  private static MobileAirtimeCoreService MobAirtimecoreServices;
  private static String CorebaseURL;
  private static String BatchCorebaseURL;
  private static String PesaLinkCorebaseURL;
  private static String MobileMoneybaseURL;
  private static String MobileAirtimebaseURL;

  public PostingDBConfiguration() {

    initializeCMDBPool();

    URI url = null;
    try {
      url = new URI(CorebaseURL);
    } catch (URISyntaxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    if (coreServices == null) {
      coreServices =
          RestClientBuilder.newBuilder()
              .baseUri(url)
              .connectTimeout(timeoutOutValue, TimeUnit.MILLISECONDS)
              .readTimeout(readtimeoutOutValue, TimeUnit.MILLISECONDS)
              .build(CoreServices.class);
    }

    try {
      url = new URI(BatchCorebaseURL);
    } catch (URISyntaxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    if (BatchcoreServices == null) {
      BatchcoreServices =
          RestClientBuilder.newBuilder()
              .baseUri(url)
              .connectTimeout(timeoutOutValue, TimeUnit.MILLISECONDS)
              .readTimeout(readtimeoutOutValue, TimeUnit.MILLISECONDS)
              .build(BatchCoreServices.class);
    }

    try {
      url = new URI(PesaLinkCorebaseURL);
    } catch (URISyntaxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    if (PesalinkcoreServices == null) {
      PesalinkcoreServices =
          RestClientBuilder.newBuilder()
              .baseUri(url)
              .connectTimeout(timeoutOutValue, TimeUnit.MILLISECONDS)
              .readTimeout(readtimeoutOutValue, TimeUnit.MILLISECONDS)
              .build(PesaLinkCoreService.class);
    }

    try {
      url = new URI(MobileMoneybaseURL);
    } catch (URISyntaxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    if (MobMoneycoreServices == null) {
      MobMoneycoreServices =
          RestClientBuilder.newBuilder()
              .baseUri(url)
              .connectTimeout(timeoutOutValue, TimeUnit.MILLISECONDS)
              .readTimeout(readtimeoutOutValue, TimeUnit.MILLISECONDS)
              .build(MobileMoneyCoreService.class);
    }

    try {
      url = new URI(MobileAirtimebaseURL);
    } catch (URISyntaxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    if (MobAirtimecoreServices == null) {
      MobAirtimecoreServices =
          RestClientBuilder.newBuilder()
              .baseUri(url)
              .connectTimeout(timeoutOutValue, TimeUnit.MILLISECONDS)
              .readTimeout(readtimeoutOutValue, TimeUnit.MILLISECONDS)
              .build(MobileAirtimeCoreService.class);
    }

    PSRTGS.setDBPool(cmDBPool);
    PSRTGS.setExternalTablenames(ActualTableName);
    PSRTGS.setInitiailizeGlobalParameters(GlobalParameters);
    PSRTGS.SetSchemaNames(coreSchema, channelSchema);
    PSRTGS.setCoreServices(coreServices);

    CbftBatch.setDBPool(cmDBPool);
    CbftBatch.SetSchemaNames(coreSchema, channelSchema);
    CbftBatchCallBack.setInitiailizeGlobalParameters(GlobalParameters);
    CbftBatchCallBack.setDBPool(cmDBPool);
    CbftBatchCallBack.SetSchemaNames(coreSchema, channelSchema);
    CbftBatchCallBack.setCoreServices(coreServices);

    DftBatch.setDBPool(cmDBPool);
    DftBatch.SetSchemaNames(coreSchema, channelSchema);
    DftBatchCallBack.setInitiailizeGlobalParameters(GlobalParameters);
    DftBatchCallBack.setDBPool(cmDBPool);
    DftBatchCallBack.SetSchemaNames(coreSchema, channelSchema);
    DftBatchCallBack.setCoreServices(coreServices);
    DftBatchCallBack.setPesaLinkCoreServices(PesalinkcoreServices);

    AccountingBatch.setDBPool(cmDBPool);
    AccountingBatch.SetSchemaNames(coreSchema, channelSchema);
    AccountingBatchCallBack.setInitiailizeGlobalParameters(GlobalParameters);
    AccountingBatchCallBack.setDBPool(cmDBPool);
    AccountingBatchCallBack.SetSchemaNames(coreSchema, channelSchema);
    AccountingBatchCallBack.setCoreServices(coreServices);

    MobileWalletBatch.setDBPool(cmDBPool);
    MobileWalletBatch.SetSchemaNames(coreSchema, channelSchema);
    MobileWalletBatchCallBack.setInitiailizeGlobalParameters(GlobalParameters);
    MobileWalletBatchCallBack.setDBPool(cmDBPool);
    MobileWalletBatchCallBack.SetSchemaNames(coreSchema, channelSchema);
    MobileWalletBatchCallBack.setCoreServices(coreServices);
    MobileWalletBatchCallBack.setMobMoneyCoreServices(MobMoneycoreServices);

    SalaryBatch.setDBPool(cmDBPool);
    SalaryBatch.SetSchemaNames(coreSchema, channelSchema);
    SalaryBatchCallBack.setInitiailizeGlobalParameters(GlobalParameters);
    SalaryBatchCallBack.setDBPool(cmDBPool);
    SalaryBatchCallBack.SetSchemaNames(coreSchema, channelSchema);
    SalaryBatchCallBack.setCoreServices(coreServices);
    SalaryBatchCallBack.setMobMoneyCoreServices(MobMoneycoreServices);
    SalaryBatchCallBack.setPesaLinkCoreServices(PesalinkcoreServices);

    InterestDetails.setDBPool(cmDBPool);
    InterestDetails.setExternalTablenames(ActualTableName);
    InterestDetails.setInitiailizeGlobalParameters(GlobalParameters);
    InterestDetails.SetSchemaNames(coreSchema, channelSchema);
    InternalTransfer.setCoreServices(coreServices);

    AccuralDetails.setDBPool(cmDBPool);
    AccuralDetails.setExternalTablenames(ActualTableName);
    AccuralDetails.setInitiailizeGlobalParameters(GlobalParameters);
    AccuralDetails.SetSchemaNames(coreSchema, channelSchema);
    AccuralDetails.setCoreServices(coreServices);

    PrePaidTopup.setDBPool(cmDBPool);
    PrePaidTopup.setExternalTablenames(ActualTableName);
    PrePaidTopup.setInitiailizeGlobalParameters(GlobalParameters);
    PrePaidTopup.SetSchemaNames(coreSchema, channelSchema);
    PrePaidTopup.setCoreServices(coreServices);

    ChequeBook.setDBPool(cmDBPool);
    ChequeBook.setExternalTablenames(ActualTableName);
    ChequeBook.setInitiailizeGlobalParameters(GlobalParameters);
    ChequeBook.SetSchemaNames(coreSchema, channelSchema);
    ChequeBook.setCoreServices(coreServices);

    ChargeTransaction.setDBPool(cmDBPool);
    ChargeTransaction.setExternalTablenames(ActualTableName);
    ChargeTransaction.setInitiailizeGlobalParameters(GlobalParameters);
    ChargeTransaction.SetSchemaNames(coreSchema, channelSchema);
    ChargeTransaction.setCoreServices(coreServices);

    UtlilityPaymentTransfer.setDBPool(cmDBPool);
    UtlilityPaymentTransfer.setExternalTablenames(ActualTableName);
    UtlilityPaymentTransfer.setInitiailizeGlobalParameters(GlobalParameters);
    UtlilityPaymentTransfer.SetSchemaNames(coreSchema, channelSchema);
    UtlilityPaymentTransfer.setCoreServices(coreServices);

    KESaccoTransfter.SetSchemaNames(coreSchema, channelSchema);
    KESaccoTransfter.setDBPool(cmDBPool);
    KESaccoTransfter.setExternalTablenames(ActualTableName);
    KESaccoTransfter.setInitiailizeGlobalParameters(GlobalParameters);
    KESaccoTransfter.setCoreServices(coreServices);

    StopChequePayment.SetSchemaNames(coreSchema, channelSchema);
    StopChequePayment.setDBPool(cmDBPool);
    StopChequePayment.setExternalTablenames(ActualTableName);
    StopChequePayment.setInitiailizeGlobalParameters(GlobalParameters);
    StopChequePayment.setCoreServices(coreServices);

    LoanRepayment.setDBPool(cmDBPool);
    LoanRepayment.setExternalTablenames(ActualTableName);
    LoanRepayment.setInitiailizeGlobalParameters(GlobalParameters);
    LoanRepayment.SetSchemaNames(coreSchema, channelSchema);
    LoanRepayment.setCoreServices(coreServices);

    InternalTransfer.setDBPool(cmDBPool);
    InternalTransfer.setExternalTablenames(ActualTableName);
    InternalTransfer.setInitiailizeGlobalParameters(GlobalParameters);
    InternalTransfer.SetSchemaNames(coreSchema, channelSchema);
    InternalTransfer.setCoreServices(coreServices);

    TaxTransaction.setDBPool(cmDBPool);
    TaxTransaction.setExternalTablenames(ActualTableName);
    TaxTransaction.setInitiailizeGlobalParameters(GlobalParameters);
    TaxTransaction.SetSchemaNames(coreSchema, channelSchema);
    TaxTransaction.setCoreServices(coreServices);

    StatutoryPayment.setDBPool(cmDBPool);
    StatutoryPayment.setExternalTablenames(ActualTableName);
    StatutoryPayment.setInitiailizeGlobalParameters(GlobalParameters);
    StatutoryPayment.SetSchemaNames(coreSchema, channelSchema);
    StatutoryPayment.setCoreServices(coreServices);

    SwiftTransaction.setDBPool(cmDBPool);
    SwiftTransaction.setExternalTablenames(ActualTableName);
    SwiftTransaction.setInitiailizeGlobalParameters(GlobalParameters);
    SwiftTransaction.SetSchemaNames(coreSchema, channelSchema);
    SwiftTransaction.setCoreServices(coreServices);

    LoopTransaction.setDBPool(cmDBPool);
    LoopTransaction.setExternalTablenames(ActualTableName);
    LoopTransaction.setInitiailizeGlobalParameters(GlobalParameters);
    LoopTransaction.SetSchemaNames(coreSchema, channelSchema);
    LoopTransaction.setCoreServices(coreServices);

    CreditCardRepayment.setDBPool(cmDBPool);
    CreditCardRepayment.setExternalTablenames(ActualTableName);
    CreditCardRepayment.setInitiailizeGlobalParameters(GlobalParameters);
    CreditCardRepayment.SetSchemaNames(coreSchema, channelSchema);
    CreditCardRepayment.setCoreServices(coreServices);

    KEPesalinkTransfer.setDBPool(cmDBPool);
    KEPesalinkTransfer.setExternalTablenames(ActualTableName);
    KEPesalinkTransfer.setInitiailizeGlobalParameters(GlobalParameters);
    KEPesalinkTransfer.SetSchemaNames(coreSchema, channelSchema);
    KEPesalinkTransfer.setCoreServices(coreServices);
    KEPesalinkTransfer.setPesaLinkCoreServices(PesalinkcoreServices);

    KEFTransfer.SetSchemaNames(coreSchema, channelSchema);
    KEFTransfer.setDBPool(cmDBPool);
    KEFTransfer.setExternalTablenames(ActualTableName);
    KEFTransfer.setInitiailizeGlobalParameters(GlobalParameters);
    KEFTransfer.setCoreServices(coreServices);

    MobileMoneyTransfer.SetSchemaNames(coreSchema, channelSchema);
    MobileMoneyTransfer.setDBPool(cmDBPool);
    MobileMoneyTransfer.setExternalTablenames(ActualTableName);
    MobileMoneyTransfer.setInitiailizeGlobalParameters(GlobalParameters);
    MobileMoneyTransfer.setCoreServices(coreServices);

    MobileAirtimeTransfer.SetSchemaNames(coreSchema, channelSchema);
    MobileAirtimeTransfer.setDBPool(cmDBPool);
    MobileAirtimeTransfer.setExternalTablenames(ActualTableName);
    MobileAirtimeTransfer.setInitiailizeGlobalParameters(GlobalParameters);
    MobileAirtimeTransfer.setCoreServices(coreServices);

    PostingServiceHealthCheck.setDBPool(cmDBPool);
  }

  @PreDestroy
  public void closePools() {
    try {
      DataSources.destroy(cmDBPool);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void initializeCMDBPool() {
    try {

      configProperties = ConfigProvider.getConfig();

      DriverManager.registerDriver(
          (java.sql.Driver)
              Class.forName(configProperties.getValue("CMDBDriver", String.class)).newInstance());
      String dbURL = configProperties.getValue("CMDBDURL", String.class);
      coreSchema = configProperties.getValue("CoreDBSChema", String.class);
      channelSchema = configProperties.getValue("ChannelDBSchema", String.class);
      Properties dbProps = new Properties();
      dbProps.setProperty("user", configProperties.getValue("CMDBUser", String.class));
      dbProps.setProperty("password", configProperties.getValue("CMDBPassword", String.class));
      // dbProps.setProperty("password", Base.decrypt(configProperties.getValue("CMDBPassword",
      // String.class)));
      javax.sql.DataSource unpooled = DataSources.unpooledDataSource(dbURL, dbProps);
      HashMap<String, Object> overrides = new HashMap<String, Object>();
      overrides.put("maxPoolSize", configProperties.getValue("CMDBPoolSize", Integer.class));
      overrides.put("idleConnectionTestPeriod", 60);
      overrides.put(
          "preferredTestQuery", configProperties.getValue("CMDBValidationQuery", String.class));
      overrides.put(
          "testConnectionOnCheckout",
          configProperties.getValue("CMDBTestConnectionOnCheckout", Boolean.class));
      overrides.put(
          "checkoutTimeout", configProperties.getValue("CMDBCheckoutTimeout", Integer.class));
      // overrides.put("checkoutTimeout", 5000);
      cmDBPool = (PooledDataSource) DataSources.pooledDataSource(unpooled, overrides);
      timeoutOutValue =
          configProperties.getValue(
              "com.nls.core.CoreServices_mp-rest_connectionTimeout", Long.class);

      readtimeoutOutValue =
          configProperties.getValue("com.nls.core.CoreServices_mp-rest_readTimeout", Long.class);

      CorebaseURL = configProperties.getValue("CORE_SERVICES_MP_REST_URL", String.class);
      BatchCorebaseURL = configProperties.getValue("BATCH_CORE_SERVICES_MP_REST_URL", String.class);
      PesaLinkCorebaseURL =
          configProperties.getValue("PESLINK_CORE_SERVICES_MP_REST_URL", String.class);
      MobileMoneybaseURL =
          configProperties.getValue("MOBILE_MONEY_CORE_SERVICES_MP_REST_URL", String.class);
      MobileAirtimebaseURL =
          configProperties.getValue("MOBILE_AIRTIME_CORE_SERVICES_MP_REST_URL", String.class);

      Connection dbConnection = null;
      try {
        System.getProperties()
            .put("com.mchange.v2.log.MLog", "com.mchange.v2.log.jdk14logging.Jdk14MLog");
        System.getProperties()
            .put(
                "com.mchange.v2.log.jdk14logging.Jdk14MLog.DEFAULT_CUTOFF_LEVEL",
                MLevel.INFO.toString());
        dbConnection = cmDBPool.getConnection();
        System.out.println("coreschema [" + coreSchema + "]");
        System.out.println("channelSchema [" + channelSchema + "]");
        InitializeUnitIDconfigs(dbConnection, coreSchema);
        InitiailizeGlobalParameters(dbConnection, channelSchema);
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (dbConnection != null) dbConnection.close();
      }

    } catch (Exception except) {
      throw new RuntimeException(except);
    }
  }

  public Set<Object> getSingletons() {
    return null;
  }

  public Set<Class<?>> getClasses() {
    return null;
  }

  private void InitializeUnitIDconfigs(Connection dbConnection, String schema) throws SQLException {
    ActualTableName = new HashMap<String, String>();

    PreparedStatement UnitCofigPS =
        dbConnection.prepareStatement(
            "select UNIT_ID || '-' || TABLE_REF "
                + " as TABLE_REFERENCE,TABLE_NAME from "
                + schema
                + ".unitid$configs ");
    ResultSet UnitCofigRS = UnitCofigPS.executeQuery();
    while (UnitCofigRS.next()) {
      ActualTableName.put(
          UnitCofigRS.getString("TABLE_REFERENCE"), UnitCofigRS.getString("TABLE_NAME"));
    }
    UnitCofigRS.close();
    UnitCofigPS.close();

    // System.out.println("Actual table is print ::::[" + ActualTableName + "]");
  }

  private void InitiailizeGlobalParameters(Connection dbConnection, String schema)
      throws SQLException {
    GlobalParameters = new LinkedHashMap<String, String>();

    PreparedStatement GlobalPs =
        dbConnection.prepareStatement("select * from " + schema + ".global$parameters ");
    ResultSet GlobalRs = GlobalPs.executeQuery();
    while (GlobalRs.next()) {
      GlobalParameters.put(GlobalRs.getString("NAME"), GlobalRs.getString("VALUE"));
    }
    GlobalRs.close();
    GlobalPs.close();
    System.out.println("GlobalParameters [" + GlobalParameters + "]");

    // return GlobalParameters;

  }
}
