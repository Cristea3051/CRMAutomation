package com.crm.FacebookAccounts.Refactor.Pages;

import com.resources.Helpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FacebookAccountsPage {

    private WebDriver driver;
    private WebDriverWait wait;


//    CreateFacebookAccountForm
     private final By accountNameField = By.cssSelector("textarea.form-control");
     private final By selectStatus = By.cssSelector("select.form-control.js-maxlength[name='status'][data-modal-field-id='create_status']");
     private final By createOtherFee = By.cssSelector("input.form-control.js-maxlength[name='other_fees'][data-modal-field-id='create_other_fees']");

    enterText(By.cssSelector("input.form-control.js-maxlength[name='username'][data-modal-field-id='create_username']"), "AutomationTestJavaUser");
    enterText(By.cssSelector("input.form-control.js-maxlength[name='password'][data-modal-field-id='create_password']"), "AutomationTestJavaPass");
    enterText(By.cssSelector("input.form-control.js-maxlength[name='email_login'][data-modal-field-id='create_email_login']"), "AutomationEmail@Login");
    enterText(By.cssSelector("input.form-control.js-maxlength[name='email_password'][data-modal-field-id='create_email_password']"), "AutomationEmail@Password");
    enterText(By.cssSelector("input.form-control.js-maxlength[name='farmer_comments'][data-modal-field-id='create_farmer_comments']"), "1comment / 2comments / 3comments");
    enterText(By.cssSelector("input.form-control.js-maxlength[name='2fa'][data-modal-field-id='create_2fa']"), "gq52limcbz2fj7iqowo6a6hzufm2vjxd");
    enterText(By.cssSelector("input.form-control.js-maxlength[name='backup_code'][data-modal-field-id='create_backup_code']"), "7781 2286 7437 6456 9022 9765 6536 1766 4432 2797 3077 2043 6864 9346 0563 6609 9405 0172 7099 1031");
    selectDropdownOption(By.cssSelector("select.js-select2.multiple-selector-inputs[name='account-cards[]'][data-modal-field-id='create_credit_cards']"), 2);
    handleAutocomplete(By.cssSelector("input.form-control.js-maxlength[inputname='batch_id'][data-modal-field-id='create_batch_id']"), "Su", By.id("autocomplete-list"));
    clickAndSelectRandomDay(By.cssSelector("input.form-control.js-maxlength[name='monthly_fees_date'][data-modal-field-id='create_monthly_fees_date']"));
    clickAndSelectRandomDay(By.cssSelector("input.form-control.js-maxlength[name='sync_from_date'][data-modal-field-id='create_sync_from_date']"));
    clickAndSelectRandomDay(By.cssSelector("input.form-control.js-maxlength[name='bh_date'][data-modal-field-id='create_bh_date']"));
    clickAndSelectRandomDay(By.cssSelector("input.form-control.js-maxlength[name='mb_delivery_date'][data-modal-field-id='create_mb_delivery_date']"));
    handleAutocomplete(By.cssSelector("input.form-control.js-maxlength[inputname='source_id'][data-modal-field-id='create_source_id']"), "FJ", By.id("autocomplete-list"));
    enterText(By.cssSelector("input.form-control.js-maxlength[name='first_last_name'][data-modal-field-id='create_first_last_name']"), "FirstNameTest LastNameTest");
    handleAutocomplete(By.cssSelector("input.form-control.js-maxlength[inputname='rdp_id'][data-modal-field-id='create_rdp_id']"), "GUL", By.id("autocomplete-list"));
    selectDropdownOption(By.cssSelector("select.form-control[name='proxy_id'][data-modal-field-id='create_proxies']"), 1);
    selectDropdownOption(By.cssSelector("select.form-control[name='account-domains[]'][data-modal-field-id='create_domains']"), 0);
    handleAutocomplete(By.cssSelector("input.form-control.js-maxlength[inputname='account_owner'][data-modal-field-id='create_account_owner']"), "And", By.id("autocomplete-list"));
    enterText(By.cssSelector("input.form-control.js-maxlength[name='mb_comments'][data-modal-field-id='create_mb_comments']"), "1commentMB / 2commentsMB / 3commentsMB");

                Helpers.waitForSeconds(3);
                driver.findElement(By.cssSelector("button#create-facebook-accounts-button")).click();
}
