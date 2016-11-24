package tests.rest.v2.protobuf

import base.RESTSpec
import protobuf.ObservationsMessageProto
import selectors.protobuf.ObservationSelector

import static config.Config.EHR_ID
import static org.hamcrest.Matchers.*
import static spock.util.matcher.HamcrestSupport.that
import static tests.rest.v2.Operator.*
import static tests.rest.v2.ValueType.*
import static tests.rest.v2.constraints.*

class ConstraintSpec extends RESTSpec{

    /**
     * TrueConstraint.class,
     BiomarkerConstraint.class,
     ModifierConstraint.class,
     FieldConstraint.class,
     ValueConstraint.class,
     TimeConstraint.class,
     PatientSetConstraint.class,
     Negation.class,
     Combination.class,
     TemporalConstraint.class,
     ConceptConstraint.class,
     StudyConstraint.class,
     NullConstraint.class
     */
    def final INVALIDARGUMENTEXCEPTION = "InvalidArgumentsException"
    def final EMPTYCONTSTRAINT = "Empty constraint parameter."

    /**
     *  when:" I do a Get with a wrong type."
     *  then: "then I get a 400 with 'Constraint not supported: BadType.'"
     */
    def "Get /query/observations malformed query"(){
        when:" I do a Get query/observations with a wrong type."
        def constraintMap = [type: 'BadType']

        def responseData = getProtobuf("query/hypercube", toQuery(constraintMap))

        then: "then I get a 400 with 'Constraint not supported: BadType.'"
        that responseData.httpStatus, is(400)
        that responseData.type, is(INVALIDARGUMENTEXCEPTION)
        that responseData.message, is('Constraint not supported: BadType.')
    }

    def "TrueConstraint.class"(){
        def constraintMap = [type: TrueConstraint]

        when:
        ObservationsMessageProto responseData = getProtobuf("query/hypercube", toQuery(constraintMap))

        then:
        ObservationSelector selector = new ObservationSelector(responseData)
        (0..<selector.cellCount).each {
            assert selector.select(it, "ConceptDimension", "conceptCode", 'String')
        }
    }

    def "BiomarkerConstraint.class"(){

    }

    def "ModifierConstraint.class"(){
        def constraintMap = [
                type: ModifierConstraint, modifierCode: "TNS:SMPL", path:"\\Public Studies\\TUMOR_NORMAL_SAMPLES\\Sample Type\\",
                values: [type: ValueConstraint, valueType: STRING, operator: EQUALS, value: "Tumor"]
        ]

        when:
        ObservationsMessageProto responseData = getProtobuf("query/hypercube", toQuery(constraintMap))

        then:
        ObservationSelector selector = new ObservationSelector(responseData)
        (0..<selector.cellCount).each {
            assert selector.select(it, "ConceptDimension", "conceptCode", 'String') == 'TNS:LAB:CELLCNT'
        }
    }

    def "FieldConstraint.class"(){
        def constraintMap = [type: FieldConstraint,
                             field: [dimension: 'PatientDimension',
                                     fieldName: 'age',
                                     type: NUMERIC ],
                             operator: LESS_THAN,
                             value:100]
        when:
        ObservationsMessageProto responseData = getProtobuf("query/hypercube", toQuery(constraintMap))

        then:
        ObservationSelector selector = new ObservationSelector(responseData)
        (0..<selector.cellCount).each {
            assert selector.select(it, "PatientDimension", "age", 'Int') < 100
        }
    }

    def "ValueConstraint.class"(){
        def constraintMap = [type: ValueConstraint, valueType: NUMERIC, operator: GREATER_THAN, value:176]
        when:
        ObservationsMessageProto responseData = getProtobuf("query/hypercube", toQuery(constraintMap))

        then:
        ObservationSelector selector = new ObservationSelector(responseData)

        (0..<selector.cellCount).each {
            assert selector.select(it) > 176
        }
    }

    def "TimeConstraint.class"(){
        def date = toDateString("01-01-2016Z")
        def constraintMap = [type: TimeConstraint,
                             field: [dimension: 'StartTimeDimension', fieldName: 'startDate', type: DATE ],
                             operator: AFTER,
                             values: [date]]
        when:
        ObservationsMessageProto responseData = getProtobuf("query/hypercube", toQuery(constraintMap))

        then:
        ObservationSelector selector = new ObservationSelector(responseData)

        (0..<selector.cellCount).each {
            assert selector.select(it, "ConceptDimension", "conceptCode", 'String') != ''
        }
    }

    def "PatientSetConstraint.class"(){
        def constraintMap = [type: PatientSetConstraint, patientSetId: 0, patientIds: -62]
        when:
        ObservationsMessageProto responseData = getProtobuf("query/hypercube", toQuery(constraintMap))

        then:
        ObservationSelector selector = new ObservationSelector(responseData)
        (0..<selector.cellCount).each {
            assert selector.select(it, "ConceptDimension", "conceptCode", 'String') != ''
        }

        when:
        constraintMap = [type: PatientSetConstraint, patientSetId: 28731]
        responseData = getProtobuf("query/hypercube", toQuery(constraintMap))
        selector = new ObservationSelector(responseData)

        then:
        (0..<selector.cellCount).each {
            assert selector.select(it, "ConceptDimension", "conceptCode", 'String') != ''
        }
    }

    def "Negation.class"(){
        def constraintMap = [
                type: Negation,
                arg: [type: PatientSetConstraint, patientSetId: 0, patientIds: -62]
        ]
        when:
        ObservationsMessageProto responseData = getProtobuf("query/hypercube", toQuery(constraintMap))

        then:
        ObservationSelector selector = new ObservationSelector(responseData)
        (0..<selector.cellCount).each {
            assert selector.select(it, "ConceptDimension", "conceptCode", 'String').equals('EHR:VSIGN:HR')
        }
    }

    def "Combination.class"(){
        def constraintMap = [
                type: Combination,
                operator: AND,
                args: [
                        [type: PatientSetConstraint, patientSetId: 0, patientIds: -62],
                        [type: ConceptConstraint, path: "\\Public Studies\\EHR\\Vital Signs\\Heart Rate\\"]
                ]
        ]
        when:
        ObservationsMessageProto responseData = getProtobuf("query/hypercube", toQuery(constraintMap))

        then:
        ObservationSelector selector = new ObservationSelector(responseData)

        (0..<selector.cellCount).each {
            assert selector.select(it, "ConceptDimension", "conceptCode", 'String').equals('EHR:VSIGN:HR')
        }
    }

    def "TemporalConstraint.class"(){
        def constraintMap = [
                type: TemporalConstraint,
                 operator: AFTER,
                 eventConstraint: [
                         type: ValueConstraint,
                         valueType: NUMERIC,
                         operator: LESS_THAN,
                         value: 60
                ]
        ]
        when:
        ObservationsMessageProto responseData = getProtobuf("query/hypercube", toQuery(constraintMap))

        then:
        ObservationSelector selector = new ObservationSelector(responseData)

        (0..<selector.cellCount).each {
            assert selector.select(it, "ConceptDimension", "conceptCode", 'String').equals('EHR:VSIGN:HR')
        }
    }

    def "ConceptConstraint.class"(){
        def constraintMap = [type: ConceptConstraint, path: "\\Public Studies\\EHR\\Vital Signs\\Heart Rate\\"]
        when:
        ObservationsMessageProto responseData = getProtobuf("query/hypercube", toQuery(constraintMap))

        then:
        ObservationSelector selector = new ObservationSelector(responseData)

        (0..<selector.cellCount).each {
            assert selector.select(it, "ConceptDimension", "conceptCode", 'String').equals('EHR:VSIGN:HR')
        }
    }

    def "StudyConstraint.class"(){
        def constraintMap = [type: StudyConstraint, studyId: EHR_ID]
        when:
        ObservationsMessageProto responseData = getProtobuf("query/hypercube", toQuery(constraintMap))

        then:
        ObservationSelector selector = new ObservationSelector(responseData)

        (0..<selector.cellCount).each {
            assert selector.select(it, "StudyDimension", "studyId", 'String').equals('EHR')
        }
    }

    def "NullConstraint.class"(){
        def constraintMap = [type: NullConstraint]

        when:
        ObservationsMessageProto responseData = getProtobuf("query/hypercube", toQuery(constraintMap))

        then:
        ObservationSelector selector = new ObservationSelector(responseData)

        (0..<selector.cellCount).each {
            assert selector.select(it, "StudyDimension", "studyId", 'String').equals('EHR')
        }
    }

}
