--
-- Type: TABLE; Owner: BIOMART; Name: BIO_ASY_ANALYSIS_PLTFM
--
 CREATE TABLE "BIOMART"."BIO_ASY_ANALYSIS_PLTFM" 
  (	"BIO_ASY_ANALYSIS_PLTFM_ID" NUMBER(18,0) NOT NULL ENABLE, 
"PLATFORM_NAME" NVARCHAR2(200), 
"PLATFORM_VERSION" NVARCHAR2(200), 
"PLATFORM_DESCRIPTION" NVARCHAR2(1000), 
 CONSTRAINT "BIO_ASSAY_ANALYSIS_PLATFORM_PK" PRIMARY KEY ("BIO_ASY_ANALYSIS_PLTFM_ID")
 USING INDEX
 TABLESPACE "INDX"  ENABLE
  ) SEGMENT CREATION IMMEDIATE
 TABLESPACE "TRANSMART" ;

--
-- Type: TRIGGER; Owner: BIOMART; Name: TRG_BIO_ASY_ANALYSIS_PLTFM_ID
--
  CREATE OR REPLACE TRIGGER "BIOMART"."TRG_BIO_ASY_ANALYSIS_PLTFM_ID" before insert on "BIO_ASY_ANALYSIS_PLTFM"    for each row begin     if inserting then       if :NEW."BIO_ASY_ANALYSIS_PLTFM_ID" is null then          select SEQ_BIO_DATA_ID.nextval into :NEW."BIO_ASY_ANALYSIS_PLTFM_ID" from dual;       end if;    end if; end;













/
ALTER TRIGGER "BIOMART"."TRG_BIO_ASY_ANALYSIS_PLTFM_ID" ENABLE;
 
