package in.vegamdigital.app.presentation.screens;

import android.content.Intent;
import android.net.Uri;
import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.outlined.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.style.TextAlign;
import in.vegamdigital.app.domain.model.*;
import in.vegamdigital.app.presentation.components.*;
import in.vegamdigital.app.presentation.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000z\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aF\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0018\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a2\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\u000e2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u0018\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\tH\u0003\u001a,\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a,\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001aH\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u001a6\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\t2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\u000e2\u0006\u0010\u0010\u001a\u00020\t2\b\b\u0002\u0010\u001c\u001a\u00020\u001dH\u0003\u001aJ\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020\t2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u001c\u0010 \u001a\u0018\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u00010\u000e\u00a2\u0006\u0002\b\"\u00a2\u0006\u0002\b#H\u0003\u001a$\u0010$\u001a\u00020\u00012\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\'0&2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a@\u0010(\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u001a,\u0010*\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a \u0010+\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010,\u001a\u00020-H\u0003\u001aT\u0010.\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u001e\u0010/\u001a\u001a\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u000100H\u0007\u001a \u00101\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010,\u001a\u00020-H\u0003\u001a,\u00102\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u00a8\u00063"}, d2 = {"AskDoubtScreen", "", "busy", "", "back", "Lkotlin/Function0;", "notifications", "submit", "Lkotlin/Function2;", "", "BonusCoursesScreen", "data", "Lin/vegamdigital/app/domain/model/Dashboard;", "navigate", "Lkotlin/Function1;", "CertificateFact", "label", "value", "CertificateScreen", "CourseDetailScreen", "course", "Lin/vegamdigital/app/domain/model/Course;", "DoubtDetailScreen", "doubt", "Lin/vegamdigital/app/domain/model/Doubt;", "answer", "Field", "change", "lines", "", "FormPage", "title", "content", "Landroidx/compose/foundation/layout/ColumnScope;", "Landroidx/compose/runtime/Composable;", "Lkotlin/ExtensionFunctionType;", "NotificationsScreen", "updates", "", "Lin/vegamdigital/app/domain/model/Update;", "PostJobScreen", "Lin/vegamdigital/app/domain/model/Job;", "ProgressScreen", "ProgressStat", "modifier", "Landroidx/compose/ui/Modifier;", "ReferralScreen", "send", "Lkotlin/Function3;", "ReferralStat", "SeniorsScreen", "app_debug"})
public final class DetailScreensKt {
    
    @androidx.compose.runtime.Composable()
    public static final void CourseDetailScreen(@org.jetbrains.annotations.NotNull()
    in.vegamdigital.app.domain.model.Course course, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> back, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> notifications) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void BonusCoursesScreen(@org.jetbrains.annotations.NotNull()
    in.vegamdigital.app.domain.model.Dashboard data, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> navigate, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> back) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void NotificationsScreen(@org.jetbrains.annotations.NotNull()
    java.util.List<in.vegamdigital.app.domain.model.Update> updates, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> back) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SeniorsScreen(@org.jetbrains.annotations.NotNull()
    in.vegamdigital.app.domain.model.Dashboard data, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> back, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> notifications) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ReferralScreen(@org.jetbrains.annotations.NotNull()
    in.vegamdigital.app.domain.model.Dashboard data, boolean busy, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> back, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> notifications, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> send) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ReferralStat(java.lang.String value, java.lang.String label, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CertificateScreen(@org.jetbrains.annotations.NotNull()
    in.vegamdigital.app.domain.model.Dashboard data, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> back, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> notifications) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CertificateFact(java.lang.String label, java.lang.String value) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ProgressScreen(@org.jetbrains.annotations.NotNull()
    in.vegamdigital.app.domain.model.Dashboard data, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> back, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> notifications) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ProgressStat(java.lang.String value, java.lang.String label, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void AskDoubtScreen(boolean busy, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> back, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> notifications, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> submit) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void DoubtDetailScreen(@org.jetbrains.annotations.NotNull()
    in.vegamdigital.app.domain.model.Doubt doubt, boolean busy, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> back, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> notifications, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> answer) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PostJobScreen(boolean busy, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> back, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> notifications, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super in.vegamdigital.app.domain.model.Job, kotlin.Unit> submit) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void FormPage(java.lang.String title, kotlin.jvm.functions.Function0<kotlin.Unit> back, kotlin.jvm.functions.Function0<kotlin.Unit> notifications, kotlin.jvm.functions.Function1<? super androidx.compose.foundation.layout.ColumnScope, kotlin.Unit> content) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void Field(java.lang.String value, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> change, java.lang.String label, int lines) {
    }
}