--
-- Type: TABLE; Owner: DEAPP; Name: DE_CONCEPT_VISIT
--
 CREATE TABLE "DEAPP"."DE_CONCEPT_VISIT" 
  (	"CONCEPT_CD" VARCHAR2(50 BYTE) NOT NULL ENABLE, 
"VISIT_NAME" VARCHAR2(100 BYTE) NOT NULL ENABLE, 
"SOURCESYSTEM_CD" VARCHAR2(50 BYTE)
  ) SEGMENT CREATION DEFERRED
 TABLESPACE "TRANSMART" ;

