/*
* @author Aegon QA
*/

Aegon REST API Test (Module - Payments)
============================================

Tools Used:

* Maven
* TestNG
* Rest Assured 
* json-simple (for reading Json file)
* Extent Report 3.1.5

Project Endpoints (Swagger):

* proposal                = https://services.qa-aegonlife.com/proposal
* Get Policy              = https://services.qa-aegonlife.com/policyv2/group/policies/{PolicyNumber}
* Get Party          	  = https://services.qa-aegonlife.com/party/parties/members/{policyHolderId}
* registerPayment         = https://services.qa-aegonlife.com/payment/payments/register
* initiatePayment         = https://services.qa-aegonlife.com/payment/payments
* quotation               = https://services.qa-aegonlife.com/proposal/retail/quotation
* paymentSummary          = https://services.qa-aegonlife.com/payment/payments/search
* getBillsForPolicyNumber = https://services.qa-aegonlife.com/payment/receivables/{policyNumber}
* searchPayouts           = https://services.qa-aegonlife.com/payment/payouts
* updateOfflinePayout     = https://services.qa-aegonlife.com/payment/payouts/offline
* initiatePayout          = https://service-api.qa-aegonlife.com/payment/internal/payouts/initiate
* getAllSuspenses         = https://services.qa-aegonlife.com/payment/suspenses?policyNumber={policyNumber}
* cancelDue               = https://services.qa-aegonlife.com/payment/receivables/cancel
* initiatePayout          = https://services.qa-aegonlife.com/payment/payouts/initiate
* mandates                = https://services.qa-aegonlife.com/payment/mandates
* aegonlife               = https://services.qa-aegonlife.com
* cancelpolicy            = https://service-api.qa-aegonlife.com/cancelpolicy
* ebao                    = https://sandbox.in.ebaocloud.com/cas/ebao


Confluence:
https://futuready.atlassian.net/wiki/spaces/AEG/pages/1212710984/Payments+Overview

Git:
https://github.com/futureadybroker/aegon-payment-suite

References: 

* https://jsoneditoronline.org/
* http://www.jsonschema2pojo.org/
* https://www.awesome-testing.com/2016/07/restful-api-testing-with-rest-assured-1.html
* https://jsonschema.net
