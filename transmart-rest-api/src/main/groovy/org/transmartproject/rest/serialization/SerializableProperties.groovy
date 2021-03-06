/* (c) Copyright 2017, tranSMART Foundation, Inc. */

package org.transmartproject.rest.serialization

/**
 * Created by piotrzakrzewski on 03/11/2016.
 */
class SerializableProperties {
    public static final Set<String> PATIENT = ["id", "birthDate", "deathDate",
                                                        "age", "race", "maritalStatus",
                                                        "religion", "sourcesystemCd", "sexCd"]
    public static final Set<String> CONCEPT = ["conceptPath", "conceptCode"]
    public static final Set<String> STUDY = ["studyId"]
    public static final Set<String> TRIAL_VISIT = ["id", "relTimeUnit", "relTime", "relTimeLabel"]
    public static final Set<String> START_DATE = ["startDate"]
    public static final Set<String> END_DATE = ["endDate"]
    public static final Set<String> VISIT = ["patient", "encounterNum", "activeStatusCd", "startDate", "endDate", "inoutCd", "locationCd"]
    public static final Set<String> LOCATION = ["locationCd"]
    public static final Set<String> PROVIDER = ["providerId"]
    public static final Set<String> BIOMARKER = ['label', 'bioMarker']
    public static final Set<String> ASSAY = ['label', 'assay']


    public static final Map<String, Set<String>> SERIALIZABLES = [
            "ConceptDimension": CONCEPT,
            "PatientDimension": PATIENT,
            "TrialVisitDimension": TRIAL_VISIT,
            "StartTimeDimension": START_DATE,
            "EndTimeDimension": END_DATE,
            "StudyDimension": STUDY,
            "VisitDimension": VISIT,
            "LocationDimension": LOCATION,
            "ProviderDimension": PROVIDER,
            "BioMarkerDimension": BIOMARKER,
            "AssayDimension": ASSAY
    ]
}
