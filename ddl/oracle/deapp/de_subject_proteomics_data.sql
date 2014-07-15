--
-- Type: TABLE; Owner: DEAPP; Name: DE_SUBJECT_PROTEOMICS_DATA
--
 CREATE TABLE "DEAPP"."DE_SUBJECT_PROTEOMICS_DATA" 
  (	"TRIAL_NAME" VARCHAR2(15 BYTE), 
"PROTEIN_ANNOTATION_ID" NUMBER(38,0), 
"COMPONENT" VARCHAR2(100 BYTE), 
"PATIENT_ID" NUMBER(38,0), 
"GENE_SYMBOL" VARCHAR2(100 BYTE), 
"GENE_ID" NUMBER(10,0), 
"ASSAY_ID" NUMBER, 
"SUBJECT_ID" VARCHAR2(100 BYTE), 
"INTENSITY" NUMBER(38,0), 
"ZSCORE" NUMBER
  ) SEGMENT CREATION DEFERRED
 TABLESPACE "TRANSMART" ;

