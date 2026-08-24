package in.vegamdigital.app.presentation.screens;

import android.content.Intent;
import android.net.Uri;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.outlined.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextOverflow;
import in.vegamdigital.app.domain.model.*;
import in.vegamdigital.app.presentation.components.*;
import in.vegamdigital.app.presentation.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000L\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u001e\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0003\u001a$\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a$\u0010\r\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a$\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u0010\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0011H\u0003\u001a$\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a2\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0007\u001a\u0010\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u0017H\u0003\u001a:\u0010\u0018\u001a\u00020\u0001*\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0003\u00a8\u0006 "}, d2 = {"CourseCard", "", "course", "Lin/vegamdigital/app/domain/model/Course;", "navigate", "Lkotlin/Function1;", "", "CourseProgressCard", "onClick", "Lkotlin/Function0;", "CoursesScreen", "data", "Lin/vegamdigital/app/domain/model/Dashboard;", "DoubtsScreen", "HomeScreen", "JobCard", "job", "Lin/vegamdigital/app/domain/model/Job;", "JobsScreen", "ProfileScreen", "logout", "UpdateCard", "update", "Lin/vegamdigital/app/domain/model/Update;", "StatCard", "Landroidx/compose/foundation/layout/RowScope;", "value", "label", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "modifier", "Landroidx/compose/ui/Modifier;", "app_debug"})
public final class MainScreensKt {
    
    @androidx.compose.runtime.Composable()
    public static final void HomeScreen(@org.jetbrains.annotations.NotNull()
    in.vegamdigital.app.domain.model.Dashboard data, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> navigate) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CourseProgressCard(in.vegamdigital.app.domain.model.Course course, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatCard(androidx.compose.foundation.layout.RowScope $this$StatCard, java.lang.String value, java.lang.String label, androidx.compose.ui.graphics.vector.ImageVector icon, androidx.compose.ui.Modifier modifier, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void UpdateCard(in.vegamdigital.app.domain.model.Update update) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CoursesScreen(@org.jetbrains.annotations.NotNull()
    in.vegamdigital.app.domain.model.Dashboard data, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> navigate) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CourseCard(in.vegamdigital.app.domain.model.Course course, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> navigate) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void JobsScreen(@org.jetbrains.annotations.NotNull()
    in.vegamdigital.app.domain.model.Dashboard data, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> navigate) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void JobCard(in.vegamdigital.app.domain.model.Job job) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void DoubtsScreen(@org.jetbrains.annotations.NotNull()
    in.vegamdigital.app.domain.model.Dashboard data, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> navigate) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ProfileScreen(@org.jetbrains.annotations.NotNull()
    in.vegamdigital.app.domain.model.Dashboard data, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> navigate, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> logout) {
    }
}