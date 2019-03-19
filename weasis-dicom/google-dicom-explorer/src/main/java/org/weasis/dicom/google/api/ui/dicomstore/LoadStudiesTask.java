package org.weasis.dicom.google.api.ui.dicomstore;

import org.weasis.dicom.google.api.GoogleAPIClient;
import org.weasis.dicom.google.api.model.DicomStore;
import org.weasis.dicom.google.api.model.StudyModel;
import org.weasis.dicom.google.api.model.StudyQuery;
import org.weasis.dicom.google.api.ui.StudyView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class LoadStudiesTask extends AbstractDicomSelectorTask<List<StudyView>> {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter TIME_FORMAT = new DateTimeFormatterBuilder()
            .appendPattern("HH[:]mm[:]ss")
            .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
            .toFormatter();

    private final DicomStore store;
    private final StudyQuery query;

    public LoadStudiesTask(DicomStore store,
                           GoogleAPIClient api,
                           DicomStoreSelector view) {
        this(store, api, view, null);
    }

    public LoadStudiesTask(DicomStore store,
                           GoogleAPIClient api,
                           DicomStoreSelector view,
                           StudyQuery studyQuery) {
        super(api, view);
        this.store = store;
        this.query = studyQuery;
    }

    @Override
    protected List<StudyView> doInBackground() throws Exception {
        List<StudyModel> studies = api.fetchStudies(store, query);

        return studies.stream().map(this::parse).collect(toList());
    }

    private StudyView parse(StudyModel model) {
        StudyView view = new StudyView();

        if (model.getStudyInstanceUID() != null) {
            view.setStudyId(model.getStudyInstanceUID().getFirstValue().orElse(""));
        }
        if (model.getPatientName() != null) {
            view.setPatientName(model.getPatientName().getFirstValue().map(StudyModel.Value::getAlphabetic).orElse(""));
        }
        if (model.getPatientId() != null) {
            view.setPatientId(model.getPatientId().getFirstValue().orElse(""));
        }
        if (model.getAccessionNumber() != null) {
            view.setAccountNumber(model.getAccessionNumber().getFirstValue().orElse(""));
        }
        if (model.getStudyDate() != null) {
            try {
                view.setStudyDate(model.getStudyDate().getFirstValue().map(s -> LocalDate.parse(s, DATE_FORMAT)).orElse(null));
            } catch (Exception e) {
                try {
                    view.setStudyDate(model.getStudyDate().getFirstValue().map(s -> LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy.MM.dd"))).orElse(null));
                } catch (Exception ignored) {
                }
            }
        }
        if (model.getStudyTime() != null) {
            try {
                view.setStudyTime(model.getStudyTime().getFirstValue().map(s -> LocalTime.parse(s, TIME_FORMAT)).orElse(null));
            } catch (Exception ignored) {
            }
        }
        if (model.getStudyDescription() != null) {
            view.setDescription(model.getStudyDescription().getFirstValue().orElse(""));
        }
        if (model.getRefPhd() != null) {
            view.setRefPhd(model.getRefPhd().getFirstValue().map(StudyModel.Value::getAlphabetic).orElse(""));
        }
        if (model.getReqPhd() != null) {
            view.setReqPhd(model.getReqPhd().getFirstValue().map(StudyModel.Value::getAlphabetic).orElse(""));
        }
        if (model.getLocation() != null) {
            view.setLocation(model.getLocation().getFirstValue().map(StudyModel.Value::getAlphabetic).orElse(""));
        }
        if (model.getBirthDate() != null) {
            try {
                view.setBirthDate(model.getBirthDate().getFirstValue().map(s -> LocalDate.parse(s, DATE_FORMAT)).orElse(null));
            } catch (Exception ignored) {
            }
        }

        return view;
    }

    @Override
    protected void onCompleted(List<StudyView> result) {
        view.updateTable(result);
    }
}
